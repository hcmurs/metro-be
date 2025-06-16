package com.example.cronjob.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaypalServiceImpl {

    @Value("${paypal.client-id}")
    private String clientId;

    @Value("${paypal.client-secret}")
    private String clientSecret;

    @Value("${paypal.base-url}")
    private String paypalBaseUrl;

    @Value("${paypal.return-url}")
    private String returnUrl;

    @Value("${paypal.cancel-url}")
    private String cancelUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public String getAccessToken() {
        String auth = Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes());

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(clientId, clientSecret);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<String> body = new HttpEntity<>("grant_type=client_credentials", headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                paypalBaseUrl + "/v1/oauth2/token",
                HttpMethod.POST,
                body,
                Map.class
        );

        return response.getBody().get("access_token").toString();
    }

    public String createOrder(Double amount, String currency, String description) {
        String accessToken = getAccessToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> orderPayload = Map.of(
                "intent", "CAPTURE",
                "purchase_units", List.of(Map.of(
                        "amount", Map.of(
                                "currency_code", currency,
                                "value", amount.toString()
                        ),
                        "description", description
                )),
                "application_context", Map.of(
                        "return_url", returnUrl,
                        "cancel_url", cancelUrl
                )
        );

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(orderPayload, headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                paypalBaseUrl + "/v2/checkout/orders",
                HttpMethod.POST,
                request,
                Map.class
        );

        List<Map<String, String>> links = (List<Map<String, String>>) response.getBody().get("links");
        for (Map<String, String> link : links) {
            if ("approve".equals(link.get("rel"))) {
                return link.get("href");
            }
        }

        throw new RuntimeException("Không lấy được approval link từ PayPal.");
    }
}
