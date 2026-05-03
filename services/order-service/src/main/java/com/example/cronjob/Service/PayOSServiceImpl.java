package com.example.cronjob.Service;

import com.example.cronjob.Config.PayOSConfig;
import com.example.cronjob.DTO.Request.PayOSRequest;
import com.example.cronjob.DTO.Request.PayOSWebhookRequest;
import com.example.cronjob.DTO.Response.PayOSResponse;
import com.example.cronjob.DTO.Response.TicketResponse;
import com.example.cronjob.Enum.OrderStatus;
import com.example.cronjob.Pojos.Orders;
import com.example.cronjob.Repository.OrdersRepository;
import com.example.cronjob.client.TicketClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import vn.payos.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import vn.payos.type.CheckoutResponseData;
import vn.payos.type.ItemData;
import vn.payos.type.PaymentData;
import vn.payos.type.Webhook;

import java.net.http.HttpHeaders;
import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class PayOSServiceImpl  implements PayOSService{
    private final OrdersRepository ordersRepository;
    private final PayOSConfig payOSConfig;
    private final TicketClient ticketClient;
    private final OrdersService ordersService;
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
        TicketResponse ticketResponse = ticketClient.getTicketById(order.getTicketId()).getData();
        if(ticketResponse == null){
            throw new EntityNotFoundException(ticketClient.getTicketById(order.getTicketId()).getMessage());
        }
        ItemData item = ItemData.builder()
                .name("Thanh toán cho đơn hàng: " + ticketResponse.name())
                .quantity(1)
                .price(order.getAmount().intValue())
                .build();
        String returnUrl = "org.com.hcmurs://callback?status=success&orderCode=" + order.getOrderId();
        String cancelUrl = "org.com.hcmurs://callback?status=cancel&orderCode=" + order.getOrderId();
        PaymentData paymentData = PaymentData.builder()
                .orderCode(order.getOrderId()) // Sử dụng orderId từ database
                .amount(order.getAmount().intValue())
                .description("Thanh toán cho đơn hàng:" )
                .items(Collections.singletonList(item))
                .cancelUrl(payOSConfig.getCancelUrl())
                .returnUrl(payOSConfig.getReturnUrl())
                .build();
        return payOS.createPaymentLink(paymentData);
    }


    @Override
    public void handlePayOSWebhook(Webhook webhookRequest) throws Exception {
        // 1. Xác thực chữ ký

        // *** SỬA LẠI DÒNG NÀY ***
        // Tên phương thức đúng là verifyPaymentWebhookData
        payOS.verifyPaymentWebhookData(webhookRequest);

        // 2. Lấy thông tin
        Long orderCode = webhookRequest.getData().getOrderCode();
        String desc = webhookRequest.getDesc();
        Orders order = ordersRepository.findByOrderId(orderCode);
        if (order == null) {
            throw new EntityNotFoundException("Order Not Found");
        }
        // 3. Cập nhật trạng thái đơn hàng
        if (desc.equalsIgnoreCase("success")) {
            System.out.println("Webhook: Order " + orderCode + " PAID. Updating status...");
            order.setStatus(OrderStatus.SUCCESSFUL);
            ordersRepository.save(order);
        } else {
            System.out.println("Webhook: Order " + orderCode + " status is " + desc + ". Updating status...");
            order.setStatus(OrderStatus.FAILED);
            ordersRepository.save(order);
        }
    }





}
