package com.example.cronjob.Service;

import com.example.cronjob.DTO.Request.StripeRequest;
import com.example.cronjob.DTO.Response.ApiResponse;
import com.example.cronjob.DTO.Response.StripeResponse;
import com.example.cronjob.DTO.Response.TicketResponse;
import com.example.cronjob.Pojos.Orders;
import com.example.cronjob.Repository.OrdersRepository;
import com.example.cronjob.client.TicketClient;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
@Slf4j
@Service
public class StipreServiceImpl implements StripeService{
    @Value("${stripe.secret}")
    private String stripeSecretKey;

    @Value("${stripe.publishable}")
    private String stripePublicKey;

    @Autowired
    OrdersRepository ordersRepository;

    @Autowired
    TicketClient ticketClient;

    @Autowired
    OrdersService ordersService;

    @Override
    public StripeResponse.StripePaymentResponse checkoutOrder(StripeRequest.ProductRequest request) {
        Orders order = ordersRepository.findByOrderId(request.orderId());
        if(order == null){
            throw new EntityNotFoundException("Order Not Found");
        }
        TicketResponse ticketResponse = ticketClient.getTicketById(request.orderId()).getData();
        if(ticketResponse == null){
            throw new EntityNotFoundException(ticketClient.getTicketById(request.orderId()).getMessage());
        }
        Stripe.apiKey = stripeSecretKey;
        SessionCreateParams.LineItem.PriceData.ProductData orderData = SessionCreateParams.LineItem.PriceData.ProductData.builder()
                .setName(ticketResponse.name())

                .build();
        BigDecimal vndAmount = order.getAmount(); // ví dụ: 100000 VND
        BigDecimal usdExchangeRate = BigDecimal.valueOf(26145); // tỉ giá hiện tại
        BigDecimal usdAmount = vndAmount.divide(usdExchangeRate, 2, RoundingMode.HALF_UP); // USD = 3.82

// Stripe yêu cầu đơn vị là CENT → nhân 100 và làm tròn
        Long usdCents = usdAmount.multiply(BigDecimal.valueOf(100))
                .setScale(0, RoundingMode.HALF_UP)
                .longValue();
        SessionCreateParams.LineItem.PriceData priceData = SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency("USD")
                .setProductData(orderData)
                .setUnitAmount(usdCents)
                .build();
        SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
                .setPriceData(priceData)
                .setQuantity(1L)
                .build();
        SessionCreateParams params = SessionCreateParams.builder().setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:3000/payment/success?session_id={CHECKOUT_SESSION_ID}")
                .setCancelUrl("http://localhost:3000/payment/failure?session_id={CHECKOUT_SESSION_ID}")
                .addLineItem(lineItem)
                .build();
        Session session = null;
        try {
            session = Session.create(params);
            // Cập nhật sessionId vào đơn hàng
            order.setStripeSessionId(session.getId());
            ordersRepository.save(order);
        } catch (StripeException e) {
            System.err.println("Failed to create session");
            throw new RuntimeException("Stripe Error: " + e.getMessage());
        }
        return StripeResponse.StripePaymentResponse.builder()
                .status("SUCCESS")
                .message("Stripe session created successfully")
                .sessionId(session.getId())
                .sessionUrl(session.getUrl())
                .build();
    }

    @Override
    public Map<String, Object> paymentCallbackSuccess(String sessionId) {
        try {
            Stripe.apiKey = stripeSecretKey;
            System.out.println("Stripe key: " + stripeSecretKey);
            Session session = Session.retrieve(sessionId);
            Map<String, Object> result = new HashMap<>();
            if ("paid".equalsIgnoreCase(session.getPaymentStatus())) {
                Orders order = ordersRepository.findByStripeSessionId(sessionId);
                if (order == null) {
                    throw new EntityNotFoundException("Order Not Found");
                }
                ordersService.updateTransactionSuccess(order.getOrderId());
                result.put("status", "success");
                result.put("message", "Payment completed successfully");
                result.put("transactionId", order.getTransaction().getTransactionId());
//                result.put("amount", Long.parseLong(String.valueOf(order.getAmount())));
                result.put("responseCode", "00");
                result.put("transactionStatus", "00");
                result.put("paymentTime", session.getCreated());
                return result;
            } else {
                result.put("status", "invalid");
                result.put("message", "Invalid signature");
                return result;
            }
        } catch (StripeException e) {
            throw new RuntimeException("Stripe error: " + e.getMessage());
        }
    }

    @Override
    public Map<String, Object> paymentCallbackFailed(String sessionId) {
        try {
            Stripe.apiKey = stripeSecretKey;
            Session session = Session.retrieve(sessionId);
            Map<String, Object> result = new HashMap<>();
            Orders order = ordersRepository.findByStripeSessionId(sessionId);
            if (order == null) {
                throw  new EntityNotFoundException("Order Not Found");
            }

            // Nếu chưa thanh toán, thì đánh dấu là fail
            if (!"paid".equalsIgnoreCase(session.getPaymentStatus())) {
                ordersService.updateTransactionFailed(order.getOrderId());
                result.put("status", "failed");
                result.put("message", "Payment failed");
                result.put("transactionId", order.getTransaction().getTransactionId());
                result.put("responseCode", "01");
                result.put("transactionStatus", "01");
                return result;
            } else {
                result.put("status", "invalid");
                result.put("message", "Invalid signature");
                return result;
            }
        } catch (StripeException e) {
            throw new RuntimeException("Stripe error: " + e.getMessage());
        }
    }

    @Override
    public StripeResponse.StripePaymentMobileResponse checkoutOrderMobile(StripeRequest.ProductRequest request) {
        log.info("⏳ Bắt đầu xử lý thanh toán cho orderId: {}", request.orderId());
        System.out.println("Đang kiểm tra orderId: " + request.orderId());

        Orders order = ordersRepository.findByOrderId(request.orderId());

        if (order == null) {
            System.out.println("Không tìm thấy order với ID = " + request.orderId());
            throw new EntityNotFoundException("Order Not Found");
        }

        BigDecimal vndAmount = order.getAmount();
        BigDecimal usdExchangeRate = BigDecimal.valueOf(26145);
        BigDecimal usdAmount = vndAmount.divide(usdExchangeRate, 2, RoundingMode.HALF_UP);
        Long usdCents = usdAmount.multiply(BigDecimal.valueOf(100)).longValue();

        Stripe.apiKey = stripeSecretKey;
        try {
            PaymentIntentCreateParams createParams = PaymentIntentCreateParams.builder()
                    .setCurrency("usd")
                    .setAmount(usdCents)
                    .setAutomaticPaymentMethods(
                            PaymentIntentCreateParams.AutomaticPaymentMethods
                                    .builder()
                                    .setEnabled(true)
                                    .build())
                    .build();

            PaymentIntent intent = PaymentIntent.create(createParams);
            log.info("✅ Tạo PaymentIntent thành công: {}", intent.getId());
            order.setStripeSessionId(intent.getId());
            ordersRepository.save(order);

            return StripeResponse.StripePaymentMobileResponse.builder()
                    .status("SUCCESS")
                    .message("PaymentIntent created successfully")
                    .sessionId(intent.getId())
                    .clientSecret(intent.getClientSecret())
                    .build();

        } catch (StripeException e) {
            log.error("❌ Stripe Exception: {}", e.getMessage(), e);

            throw new RuntimeException("Stripe Error: " + e.getMessage());
        }

    }

    @Override
    public Map<String, Object> paymentCallbackSuccessMobile(String sessionId) {
        try {
            Stripe.apiKey = stripeSecretKey;
            PaymentIntent intent = PaymentIntent.retrieve(sessionId);

            Map<String, Object> result = new HashMap<>();
            if ("succeeded".equalsIgnoreCase(intent.getStatus())) {
                Orders order = ordersRepository.findByStripeSessionId(sessionId);
                if (order == null) {
                    throw new EntityNotFoundException("Order Not Found");
                }

                ordersService.updateTransactionSuccess(order.getOrderId());

                result.put("status", "success");
                result.put("message", "Payment completed successfully");
                result.put("transactionId", order.getTransaction().getTransactionId());
                result.put("responseCode", "00");
                result.put("transactionStatus", "00");
                result.put("paymentTime", intent.getCreated());
                return result;
            } else {
                result.put("status", "invalid");
                result.put("message", "Payment not completed");
                return result;
            }

        } catch (StripeException e) {
            throw new RuntimeException("Stripe error: " + e.getMessage());
        }
    }

    @Override
    public Map<String, Object> paymentCallbackFailedMobile(String sessionId) {
        try {
            Stripe.apiKey = stripeSecretKey;
            PaymentIntent intent = PaymentIntent.retrieve(sessionId);

            Map<String, Object> result = new HashMap<>();
            Orders order = ordersRepository.findByStripeSessionId(sessionId);
            if (order == null) {
                throw new EntityNotFoundException("Order Not Found");
            }

            if (!"succeeded".equalsIgnoreCase(intent.getStatus())) {
                ordersService.updateTransactionFailed(order.getOrderId());
                result.put("status", "failed");
                result.put("message", "Payment failed or canceled");
                result.put("transactionId", order.getTransaction().getTransactionId());
                result.put("responseCode", "01");
                result.put("transactionStatus", "01");
                return result;
            } else {
                result.put("status", "invalid");
                result.put("message", "Invalid status");
                return result;
            }

        } catch (StripeException e) {
            throw new RuntimeException("Stripe error: " + e.getMessage());
        }
    }
}
