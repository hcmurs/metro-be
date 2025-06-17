package com.example.cronjob.Service;

import com.example.cronjob.Pojos.Orders;
import com.example.cronjob.Repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaypalServiceImpl {
    @Autowired
    private OrdersService ordersService;

    @Autowired
    private OrdersRepository ordersRepository;

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

    public String createOrder(Long orderId, String currency, String description) {
        Orders order = ordersRepository.findByOrderId(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Order not found with ID: " + orderId);
        }
        String accessToken = getAccessToken();
        BigDecimal amount = order.getAmount().divide(new BigDecimal(25000),2, RoundingMode.HALF_UP);

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
                        "description", description,
                        "custom_id", Long.toString(orderId)
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
