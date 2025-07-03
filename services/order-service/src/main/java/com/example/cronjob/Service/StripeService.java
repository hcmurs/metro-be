package com.example.cronjob.Service;

import com.example.cronjob.DTO.Response.TicketResponse;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripeService {
    //stripe - API
    // -> productName, amount, quantity, currency
    // -> returns a sessionId and url to redirect the user to Stripe Checkout

    public record StripePaymentIntentResponse(
        String status,
        String message,
        String clientSecret,
        String publishableKey
    ) {}

    public record StripePaymentIntentResponseV2(
        String status,
        String message,
        String clientSecret,
        String publishableKey,
        String paymentIntentId
    ) {}

    public record ProductRequest(
        Long amount,
        Long quantity,
        String name,
        String currency
    ) {

    }

    public class OrderPaymentResponse {
        private Long orderId;
        private String ticketCode;
        private BigDecimal totalAmount;
        private String currency;
        private String clientSecret; // Stripe PaymentIntent client secret
        private String publishableKey; // Stripe publishable key
        private String paymentIntentId; // For tracking
        private TicketResponse ticketDetails;

        // Constructors, getters, setters
    }

    // 3. PAYMENT CONFIRMATION REQUEST
    public class PaymentConfirmationRequest {
        private String paymentIntentId;
        private Long orderId;
    }


    @Value("${stripe.secret-key}")
    private String secretKey;

    @Value("${stripe.publishable-key}")
    private String publishableKey;

    public StripePaymentIntentResponse createPaymentIntent(ProductRequest productRequest) {
        Stripe.apiKey = secretKey;

        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
            .setAmount(productRequest.amount())
            .setCurrency(
                productRequest.currency() == null ? "usd" : productRequest.currency()
            )
            .build();

        try {
            PaymentIntent intent = PaymentIntent.create(params);
            return new StripePaymentIntentResponse(
                "SUCCESS", "PaymentIntent created",
                intent.getClientSecret(), publishableKey);
        } catch (StripeException e) {
            return new StripePaymentIntentResponse(
                "FAILURE",
                "Failed to create PaymentIntent: " + e.getMessage(),
                null,
                null
            );
        }
    }

    public StripePaymentIntentResponseV2 createPaymentIntentForOrder(
        Long orderId,
        String ticketCode,
        BigDecimal amount,
        String currency,
        String customerEmail) {

        Stripe.apiKey = secretKey;

        // Convert amount to cents (Stripe requirement)
        long amountInCents = amount.multiply(BigDecimal.valueOf(100)).longValue();

        PaymentIntentCreateParams.Builder paramsBuilder = PaymentIntentCreateParams.builder()
            .setAmount(amountInCents)
            .setCurrency(currency.toLowerCase())
            .setAutomaticPaymentMethods(
                PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                    .setEnabled(true)
                    .build()
            );

        // Add metadata for order tracking
        Map<String, String> metadata = new HashMap<>();
        metadata.put("orderId", orderId.toString());
        metadata.put("ticketCode", ticketCode);
        metadata.put("source", "mobile_ticket_app");
        paramsBuilder.putAllMetadata(metadata);

        // Add customer email if provided
        if (customerEmail != null && !customerEmail.isEmpty()) {
            paramsBuilder.setReceiptEmail(customerEmail);
        }

        try {
            PaymentIntent intent = PaymentIntent.create(paramsBuilder.build());
            return new StripePaymentIntentResponseV2(
                "SUCCESS",
                "PaymentIntent created successfully",
                intent.getClientSecret(),
                publishableKey,
                intent.getId()
            );
        } catch (StripeException e) {
            return new StripePaymentIntentResponseV2(
                "FAILURE",
                "Failed to create PaymentIntent: " + e.getMessage(),
                null,
                null,
                null
            );
        }
    }

    public PaymentIntent verifyPayment(String paymentIntentId) throws StripeException {
        Stripe.apiKey = secretKey;
        return PaymentIntent.retrieve(paymentIntentId);
    }

}
