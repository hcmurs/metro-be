package com.example.cronjob.Service;

import com.example.cronjob.Config.PayOSConfig;
import com.example.cronjob.DTO.Request.PayOSRequest;
import com.example.cronjob.DTO.Response.TicketResponse;
import com.example.cronjob.Pojos.Orders;
import com.example.cronjob.Repository.OrdersRepository;
import com.example.cronjob.client.TicketClient;
import jakarta.annotation.PostConstruct;
import vn.payos.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import vn.payos.type.CheckoutResponseData;
import vn.payos.type.ItemData;
import vn.payos.type.PaymentData;

import java.net.http.HttpHeaders;
import java.time.Instant;
import java.util.Collections;
import java.util.Date;

@RequiredArgsConstructor
@Service
public class PayOSServiceImpl  implements PayOSService{
    private final OrdersRepository ordersRepository;
    private final PayOSConfig payOSConfig;
    private final TicketClient ticketClient;
    private PayOS payOS;
    @PostConstruct
    public void initPayOS() {
        this.payOS = new PayOS(
                payOSConfig.getClientId(),
                payOSConfig.getApiKey(),
                payOSConfig.getChecksumKey()
        );
    }

    @Override
    public CheckoutResponseData createPaymentLink(PayOSRequest payOSRequest) throws Exception {
        Orders order = ordersRepository.findByOrderId(payOSRequest.getOrderId());
        if (order == null) {
            throw new EntityNotFoundException("Order Not Found");
        }
//        TicketResponse ticketResponse = ticketClient.getTicketById(payOSRequest.getOrderId()).getData();
//        if(ticketResponse == null){
//            throw new EntityNotFoundException(ticketClient.getTicketById(payOSRequest.getOrderId()).getMessage());
//        }
        ItemData item = ItemData.builder()
                .name("Thanh toán cho đơn hàng: ")
                .quantity(1)
                .price(order.getAmount().intValue())
                .build();

        PaymentData paymentData = PaymentData.builder()
                .orderCode(order.getOrderId()) // Sử dụng orderId từ database
                .amount(order.getAmount().intValue())
                .description("Thanh toán cho đơn hàng: " )
                .items(Collections.singletonList(item))
                .cancelUrl(payOSConfig.getCancelUrl()) // Lấy từ request
                .returnUrl(payOSConfig.getReturnUrl()) // Lấy từ request
                .build();
        return payOS.createPaymentLink(paymentData);

    }
}
