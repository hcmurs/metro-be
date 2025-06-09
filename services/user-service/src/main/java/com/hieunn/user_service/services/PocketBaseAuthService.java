package com.hieunn.user_service.services;

import com.hieunn.user_service.configs.PocketBaseProperties;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PocketBaseAuthService {

    String cachedToken;
    Instant tokenExpiry = Instant.EPOCH;
    final PocketBaseProperties pocketBaseProperties;

    public String getSuperUserToken() {
        try {
            if (cachedToken == null || Instant.now().isAfter(tokenExpiry)) {
                RestTemplate restTemplate = new RestTemplate();
                Map<String, String> body = Map.of(
                    "identity", pocketBaseProperties.getSuperuser().getEmail(),
                    "password", pocketBaseProperties.getSuperuser().getPassword()
                );

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);

                HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

                ResponseEntity<Map> response = restTemplate.postForEntity(
                    "https://pkb.lch.id.vn/api/collections/_superusers/auth-with-password",
                    request,
                    Map.class
                );

                Map data = response.getBody();
                this.cachedToken = (String) data.get("token");
                this.tokenExpiry = Instant.now().plus(Duration.ofMinutes(10)); // same as FE cache
            }

            return cachedToken;
        } catch (Exception e) {
            System.out.println("Error fetching superuser token: " + e.getMessage());
            return null;
        }
    }
}

