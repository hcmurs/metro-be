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
import com.hieunn.user_service.utils.JwtUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    JwtUtil jwtUtil;
    PasswordEncoder passwordEncoder;
    RedisTemplate<String, String> redisTemplate;

    @Override
    @Transactional
    public UserDto processSocialLogin(SocialLoginRequest socialLoginRequest) {
        if (socialLoginRequest.getAuthProvider() != AuthProvider.GOOGLE) {
            return null;
        }

        Optional<User> userByGoogleId = userRepository.findByGoogleId(socialLoginRequest.getProviderId());
        if (userByGoogleId.isPresent()) {
            User existingUser = userByGoogleId.get();
            return updateExistingGoogleUser(existingUser, socialLoginRequest);
        }

        Optional<User> userByEmail = userRepository.findByEmail(socialLoginRequest.getEmail());
        if (userByEmail.isPresent()) {
            User existingUser = userByEmail.get();
            return updateExistingGoogleUser(existingUser, socialLoginRequest);
        }

        User newUser = createNewUserFromGoogle(socialLoginRequest);
        User savedUser = userRepository.save(newUser);
        return userMapper.toUserDto(savedUser);
    }

    private UserDto updateExistingGoogleUser(User existingUser, SocialLoginRequest socialLoginRequest) {
        boolean changed = false;
        if (existingUser.getName() == null || !socialLoginRequest.getName().equals(existingUser.getName())) {
            existingUser.setName(socialLoginRequest.getName());
            changed = true;
        }
        if (existingUser.getAuthProvider() != AuthProvider.GOOGLE) {
            existingUser.setAuthProvider(AuthProvider.GOOGLE);
            changed = true;
        }
        if (existingUser.getGoogleId() == null || !existingUser.getGoogleId().equals(socialLoginRequest.getProviderId())) {
            existingUser.setGoogleId(socialLoginRequest.getProviderId());
            changed = true;
        }
        if (existingUser.getPictureUrl() == null || !socialLoginRequest.getPictureUrl().equals(existingUser.getPictureUrl())) {
            existingUser.setPictureUrl(socialLoginRequest.getPictureUrl());
            changed = true;
        }

        if (changed) {
            userRepository.save(existingUser);
        }

        return userMapper.toUserDto(existingUser);
    }

    private User createNewUserFromGoogle(SocialLoginRequest request) {
        return User.builder()
                .email(request.getEmail())
                .name(request.getName())
                .googleId(request.getProviderId())
                .authProvider(AuthProvider.GOOGLE)
                .pictureUrl(request.getPictureUrl())
                .role("ROLE_CUSTOMER")
                .build();
    }

    @Override
    public UserDto findUser(String token) {
        Long userId = jwtUtil.extractUserId(token);
        Optional<User> userById = userRepository.findById(userId);

        if (userById.isPresent()) {
            return userMapper.toUserDto(userById.get());
        } else {
            throw new CustomException(
                    ErrorMessage.USER_NOT_FOUND.getStatus(),
                    ErrorMessage.USER_NOT_FOUND.getMessage()
            );
        }
    }

    @Override
    @Transactional
    public UserDto register(RegisterRequest registerRequest) {
        if (redisTemplate.opsForValue().get(registerRequest.getEmail()) == null) {
            throw new CustomException(
                    ErrorMessage.EMAIL_NOT_VERIFIED.getStatus(),
                    ErrorMessage.EMAIL_NOT_VERIFIED.getMessage());
        }
        User newUser = createNewUserFromLocal(registerRequest);
        User savedUser = userRepository.save(newUser);

        redisTemplate.delete(registerRequest.getEmail());

        return userMapper.toUserDto(savedUser);
    }

    @Override
    public boolean isUsernameExist(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean isEmailExist(String email) {
        return userRepository.existsByEmail(email);
    }

    private User createNewUserFromLocal(RegisterRequest request) {
        return User.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .authProvider(AuthProvider.LOCAL)
                .role("ROLE_CUSTOMER")
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
}
