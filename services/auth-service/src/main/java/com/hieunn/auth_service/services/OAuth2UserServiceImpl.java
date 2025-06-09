package com.hieunn.auth_service.services;

import com.hieunn.auth_service.dtos.SocialLoginUserRequest;
import com.hieunn.auth_service.dtos.UserDto;
import com.hieunn.auth_service.exceptions.CustomException;
import com.hieunn.auth_service.exceptions.ErrorMessage;
import com.hieunn.auth_service.feignClients.UserServiceClient;
import com.hieunn.auth_service.models.AuthProvider;
import com.hieunn.auth_service.models.OAuth2UserImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OAuth2UserServiceImpl implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    UserServiceClient userServiceClient;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);
        return processOAuth2User(userRequest, oAuth2User);
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        // Registration ID is Google, Facebook, etc.
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        //Attributes from the OAuth2 user
        Map<String, Object> attributes = oAuth2User.getAttributes();

        String providerId;
        String email;
        String name;
        AuthProvider authProvider;
        String pictureUrl;

        if (AuthProvider.GOOGLE.name().equalsIgnoreCase(registrationId)) {
            authProvider = AuthProvider.GOOGLE;
            providerId = (String) attributes.get("sub");
            email = (String) attributes.get("email");
            name = (String) attributes.get("name");
            pictureUrl = (String) attributes.get("picture");

        } else {
            throw new CustomException(
                    ErrorMessage.UNSUPPORTED_AUTH_PROVIDER.getStatus(),
                    ErrorMessage.UNSUPPORTED_AUTH_PROVIDER.getMessage() + registrationId
            );
        }

        SocialLoginUserRequest socialLoginRequest = new SocialLoginUserRequest(email, name, providerId, pictureUrl, authProvider);
        UserDto userDto = userServiceClient.processSocialLogin(socialLoginRequest).getData();

        return OAuth2UserImpl.builder()
                .userDto(userDto)
                .attributes(attributes)
                .build();
    }
}