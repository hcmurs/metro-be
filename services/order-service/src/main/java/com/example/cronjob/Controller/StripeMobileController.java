package com.example.cronjob.Controller;

import com.example.cronjob.Service.StripeService;
import com.example.cronjob.Service.StripeService.ProductRequest;
import com.example.cronjob.Service.StripeService.StripePaymentIntentResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/stripe")
@Tag(
    name = "stripe",
    description = "API for handling Stripe payment intents in mobile applications"
)
@RequiredArgsConstructor
public class StripeMobileController {

    private final StripeService stripeService;

    @PostMapping("/payment-intent")
    public ResponseEntity<StripePaymentIntentResponse> createPaymentIntent(@RequestBody ProductRequest productRequest) {
        var response = stripeService.createPaymentIntent(productRequest);
        return ResponseEntity.ok(response);
    }


}

