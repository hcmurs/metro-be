package com.hieunn.user_service.services;

import com.hieunn.user_service.dtos.requests.SocialLoginUserRequest;
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

    @Override
    @Transactional
    public UserDto processSocialLogin(SocialLoginUserRequest socialLoginUserRequest) {
        if (socialLoginUserRequest.getAuthProvider() != AuthProvider.GOOGLE) {
            return null;
        }

        Optional<User> userByGoogleId = userRepository.findByGoogleId(socialLoginUserRequest.getProviderId());
        if (userByGoogleId.isPresent()) {
            User existingUser = userByGoogleId.get();
            return updateExistingGoogleUser(existingUser, socialLoginUserRequest);
        }

        User newUser = createNewUserFromGoogle(socialLoginUserRequest);
        User savedUser = userRepository.save(newUser);
        return userMapper.toUserDto(savedUser);
    }

    private UserDto updateExistingGoogleUser(User existingUser, SocialLoginUserRequest socialLoginUserRequest) {
        boolean changed = false;
        if (!socialLoginUserRequest.getName().equals(existingUser.getName())) {
            existingUser.setName(socialLoginUserRequest.getName());
            changed = true;
        }
        if (existingUser.getAuthProvider() != AuthProvider.GOOGLE) {
            existingUser.setAuthProvider(AuthProvider.GOOGLE);
            changed = true;
        }
        if (!existingUser.getGoogleId().equals(socialLoginUserRequest.getProviderId())) {
            existingUser.setGoogleId(socialLoginUserRequest.getProviderId());
            changed = true;
        }
        if (!socialLoginUserRequest.getPictureUrl().equals(existingUser.getPictureUrl())) {
            existingUser.setPictureUrl(socialLoginUserRequest.getPictureUrl());
            changed = true;
        }

        if (changed) {
            userRepository.save(existingUser);
        }

        return userMapper.toUserDto(existingUser);
    }

    private User createNewUserFromGoogle(SocialLoginUserRequest request) {
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
}
