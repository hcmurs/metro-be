package com.hieunn.auth_service.services;

import com.hieunn.auth_service.dtos.requests.SocialLoginUserRequest;
import com.hieunn.auth_service.dtos.responses.UserDto;
import com.hieunn.auth_service.exceptions.CustomException;
import com.hieunn.auth_service.exceptions.ErrorMessage;
import com.hieunn.auth_service.feignClients.UserServiceClient;
import com.hieunn.auth_service.models.AuthProvider;
import com.hieunn.auth_service.models.CustomOAuth2User;
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
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    UserServiceClient userServiceClient;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(oAuth2UserRequest);
        return processOAuth2User(oAuth2UserRequest, oAuth2User);
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        // Registration ID is Google, Facebook, etc.
        String registrationId = oAuth2UserRequest.getClientRegistration().getRegistrationId();

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

        } else if (AuthProvider.FACEBOOK.name().equalsIgnoreCase(registrationId)) {
            authProvider = AuthProvider.FACEBOOK;
            providerId = (String) attributes.get("id");
            email = (String) attributes.get("email");
            name = (String) attributes.get("name");

            @SuppressWarnings("unchecked")
            Map<String, Object> pictureObj = (Map<String, Object>) attributes.get("picture");
            @SuppressWarnings("unchecked")
            Map<String, Object> dataObj = (Map<String, Object>) pictureObj.get("data");
            pictureUrl = (String) dataObj.get("url");

        } else {
            throw new CustomException(
                    ErrorMessage.UNSUPPORTED_AUTH_PROVIDER.getStatus(),
                    ErrorMessage.UNSUPPORTED_AUTH_PROVIDER.getMessage() + registrationId);
        }

        SocialLoginUserRequest socialLoginRequest = new SocialLoginUserRequest(email, name, providerId, pictureUrl, authProvider);
        UserDto userDto = userServiceClient.processSocialLogin(socialLoginRequest).getData();

        return CustomOAuth2User.builder()
                .userDto(userDto)
                .attributes(attributes)
                .build();
    }
}