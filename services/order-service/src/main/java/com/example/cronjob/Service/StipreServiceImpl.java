package com.example.cronjob.Service;

import com.example.cronjob.DTO.Request.StripeRequest;
import com.example.cronjob.DTO.Response.StripeResponse;
import com.example.cronjob.DTO.Response.TicketResponse;
import com.example.cronjob.Pojos.Orders;
import com.example.cronjob.Repository.OrdersRepository;
import com.example.cronjob.client.TicketClient;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
                .setSuccessUrl("http://localhost:8080/success")
                .setCancelUrl("http://localhost:8080/cancel")
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
    public String paymentCallbackSuccess(String sessionId) {
        try {
            Stripe.apiKey = stripeSecretKey;
            Session session = Session.retrieve(sessionId);

            if ("paid".equalsIgnoreCase(session.getPaymentStatus())) {
                Orders order = ordersRepository.findByStripeSessionId(sessionId);
                if (order == null) {
                    throw new EntityNotFoundException("Order Not Found");
                }
                ordersService.updateTransactionSuccess(order.getOrderId());
                return "Payment success for orderId: " + order.getOrderId();
            } else {
                return "Payment not completed";
            }
        } catch (StripeException e) {
            throw new RuntimeException("Stripe error: " + e.getMessage());
        }
    }

    @Override
    public String paymentCallbackFailed(String sessionId) {
        try {
            Stripe.apiKey = stripeSecretKey;
            Session session = Session.retrieve(sessionId);

            Orders order = ordersRepository.findByStripeSessionId(sessionId);
            if (order == null) {
                throw  new EntityNotFoundException("Order Not Found");
            }

            // Nếu chưa thanh toán, thì đánh dấu là fail
            if (!"paid".equalsIgnoreCase(session.getPaymentStatus())) {
                ordersService.updateTransactionFailed(order.getOrderId());
                return "Payment failed for orderId: " + order.getOrderId();
            } else {
                return "Payment was successful, not failed";
            }
        } catch (StripeException e) {
            throw new RuntimeException("Stripe error: " + e.getMessage());
        }
    }
}
