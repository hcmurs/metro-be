package com.hieunn.user_service.services;

import com.hieunn.user_service.configs.PocketBaseProperties;
import com.hieunn.user_service.dtos.requests.BlogDTO;
import com.hieunn.user_service.dtos.requests.BlogDTO.BlogPageResPB;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PocketBaseService {

    RestTemplate restTemplate;
    PocketBaseAuthService authService;
    PocketBaseProperties pocketBaseProperties;

    public BlogDTO.BlogPageResPB getBlogs(int page, int perPage) {
        String token = authService.getSuperUserToken();
        String url = String.format(
            "%s/api/collections/blogs/records?page=%d&perPage=%d", pocketBaseProperties.getUrl(),
            page,
            perPage);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        // Use ParameterizedTypeReference for proper type handling
        ParameterizedTypeReference<BlogPageResPB> responseType =
            new ParameterizedTypeReference<>() {
            };

        return restTemplate.exchange(url, HttpMethod.GET, entity, responseType).getBody();
    }


    public BlogDTO.BlogRes getById(String id) {
        String token = authService.getSuperUserToken();
        String url = String.format("%s/api/collections/blogs/records/%s", pocketBaseProperties.getUrl(), id);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        // Use ParameterizedTypeReference for proper type handling
        ParameterizedTypeReference<BlogDTO.BlogRes> responseType =
            new ParameterizedTypeReference<BlogDTO.BlogRes>() {
            };

        return restTemplate.exchange(url, HttpMethod.GET, entity, responseType).getBody();
    }
}