package com.hieunn.user_service.services;

import com.hieunn.user_service.dtos.requests.LocalLoginRequest;
import com.hieunn.user_service.dtos.requests.RegisterRequest;
import com.hieunn.user_service.dtos.requests.SocialLoginRequest;
import com.hieunn.user_service.dtos.responses.UserDto;
import com.hieunn.user_service.exceptions.CustomException;
import com.hieunn.user_service.exceptions.ErrorMessage;
import com.hieunn.user_service.mappers.UserMapper;
import com.hieunn.user_service.models.AuthProvider;
import com.hieunn.user_service.models.User;
import com.hieunn.user_service.repositories.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    RedisTemplate<String, String> redisTemplate;

    @Override
    @Transactional
    public UserDto processSocialLogin(SocialLoginRequest socialLoginRequest) {
        AuthProvider provider = socialLoginRequest.getAuthProvider();

        if (provider != AuthProvider.GOOGLE && provider != AuthProvider.FACEBOOK) {
            return null;
        }

        Optional<User> userByProviderId = findUserByProviderId(provider, socialLoginRequest.getProviderId());
        if (userByProviderId.isPresent()) {
            return updateExistingUser(userByProviderId.get(), socialLoginRequest);
        }

        Optional<User> userByEmail = userRepository.findByEmail(socialLoginRequest.getEmail());
        if (userByEmail.isPresent()) {
            return updateExistingUser(userByEmail.get(), socialLoginRequest);
        }

        User newUser = createNewUserFromSocial(socialLoginRequest);
        return userMapper.toUserDto(userRepository.save(newUser));
    }

    private Optional<User> findUserByProviderId(AuthProvider provider, String providerId) {
        return switch (provider) {
            case GOOGLE -> userRepository.findByGoogleId(providerId);
            case FACEBOOK -> userRepository.findByFacebookId(providerId);
            default -> Optional.empty();
        };
    }

    private UserDto updateExistingUser(User existingUser, SocialLoginRequest socialLoginRequest) {
        boolean changed = false;

        if (existingUser.getName() == null || !existingUser.getName().equals(socialLoginRequest.getName())) {
            existingUser.setName(socialLoginRequest.getName());
            changed = true;
        }

        if (existingUser.getPictureUrl() == null || !existingUser.getPictureUrl().equals(socialLoginRequest.getPictureUrl())) {
            existingUser.setPictureUrl(socialLoginRequest.getPictureUrl());
            changed = true;
        }

        if (existingUser.getAuthProvider() != socialLoginRequest.getAuthProvider()) {
            existingUser.setAuthProvider(socialLoginRequest.getAuthProvider());
            changed = true;
        }

        if (socialLoginRequest.getAuthProvider() == AuthProvider.GOOGLE &&
                (existingUser.getGoogleId() == null || !existingUser.getGoogleId().equals(socialLoginRequest.getProviderId()))) {
            existingUser.setGoogleId(socialLoginRequest.getProviderId());
            changed = true;
        }

        if (socialLoginRequest.getAuthProvider() == AuthProvider.FACEBOOK &&
                (existingUser.getFacebookId() == null || !existingUser.getFacebookId().equals(socialLoginRequest.getProviderId()))) {
            existingUser.setFacebookId(socialLoginRequest.getProviderId());
            changed = true;
        }

        if (changed) {
            userRepository.save(existingUser);
        }

        return userMapper.toUserDto(existingUser);
    }

    private User createNewUserFromSocial(SocialLoginRequest request) {
        User.UserBuilder builder = User.builder()
                .email(request.getEmail())
                .name(request.getName())
                .authProvider(request.getAuthProvider())
                .pictureUrl(request.getPictureUrl());

        if (request.getAuthProvider() == AuthProvider.GOOGLE) {
            builder.googleId(request.getProviderId());
        } else if (request.getAuthProvider() == AuthProvider.FACEBOOK) {
            builder.facebookId(request.getProviderId());
        }

        return builder.build();
    }

    @Override
    public User getCurrentUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        User currentUser = (User) securityContext.getAuthentication().getPrincipal();

        if (currentUser == null) {
            throw new CustomException(
                    ErrorMessage.UNAUTHENTICATED.getStatus(),
                    ErrorMessage.UNAUTHENTICATED.getMessage());
        }

        return currentUser;
    }

    @Override
    public UserDto getCurrentUserDto() {
        return userMapper.toUserDto(this.getCurrentUser());
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserDto> findAll() {
        List<User> users = userRepository.findAll();

        return users
                .stream()
                .map(userMapper::toUserDto)
                .toList();
    }

    @Override
    @Transactional
    public UserDto register(RegisterRequest registerRequest) {
        if (redisTemplate.opsForValue().get(registerRequest.getEmail()) == null) {
            throw new CustomException(
                    ErrorMessage.EMAIL_NOT_VERIFIED.getStatus(),
                    ErrorMessage.EMAIL_NOT_VERIFIED.getMessage());
        }
        redisTemplate.delete(registerRequest.getEmail());

        User newUser = createNewUserFromLocal(registerRequest);
        userRepository.save(newUser);

        return userMapper.toUserDto(newUser);
    }

    @Override
    public boolean isUsernameExist(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean isEmailExist(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    @Transactional
    public void resetPassword(String email, String newPassword) {
        Optional<User> userByEmail = userRepository.findByEmail(email);
        if (userByEmail.isEmpty()) {
            throw new CustomException(
                    ErrorMessage.USER_NOT_FOUND.getStatus(),
                    ErrorMessage.USER_NOT_FOUND.getMessage());
        }

        if (redisTemplate.opsForValue().get(email) == null) {
            throw new CustomException(
                    ErrorMessage.EMAIL_NOT_VERIFIED.getStatus(),
                    ErrorMessage.EMAIL_NOT_VERIFIED.getMessage());
        }
        redisTemplate.delete(email);

        User user = userByEmail.get();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public UserDto findByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(
                        ErrorMessage.USER_NOT_FOUND.getStatus(),
                        ErrorMessage.USER_NOT_FOUND.getMessage()));
        return userMapper.toUserDto(user);
    }

    @Override
    public UserDto findByEmail() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        User currentUser = (User) securityContext.getAuthentication().getPrincipal();
        User userFetch = userRepository.findByEmail(currentUser.getEmail())
                .orElseThrow(() -> new CustomException(
                        ErrorMessage.USER_NOT_FOUND.getStatus(),
                        ErrorMessage.USER_NOT_FOUND.getMessage()));

        if (userFetch == null) {
            throw new CustomException(
                    ErrorMessage.UNAUTHENTICATED.getStatus(),
                    ErrorMessage.UNAUTHENTICATED.getMessage());
        }

        return userMapper.toUserDto(userFetch);
    }

    private User createNewUserFromLocal(RegisterRequest request) {
        return User.builder()
                .email(request.getEmail())
                .name(request.getName())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .authProvider(AuthProvider.LOCAL)
                .build();
    }

    @Override
    public UserDto processLocalLogin(LocalLoginRequest localLoginRequest) {
        Optional<User> userByUsername = userRepository.findByUsername(localLoginRequest.getUsernameOrEmail());
        Optional<User> userByEmail = userRepository.findByEmail(localLoginRequest.getUsernameOrEmail());
        if (userByUsername.isEmpty() && userByEmail.isEmpty()) {
            throw new CustomException(
                    ErrorMessage.INCORRECT_USERNAME_OR_PASSWORD.getStatus(),
                    ErrorMessage.INCORRECT_USERNAME_OR_PASSWORD.getMessage());
        }

        User user = userByUsername.orElseGet(userByEmail::get);
        if (!passwordEncoder.matches(localLoginRequest.getPassword(), user.getPassword())) {
            throw new CustomException(
                    ErrorMessage.INCORRECT_USERNAME_OR_PASSWORD.getStatus(),
                    ErrorMessage.INCORRECT_USERNAME_OR_PASSWORD.getMessage());
        }

        return userMapper.toUserDto(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(
                        ErrorMessage.USER_NOT_FOUND.getStatus(),
                        ErrorMessage.USER_NOT_FOUND.getMessage()));
    }
}
