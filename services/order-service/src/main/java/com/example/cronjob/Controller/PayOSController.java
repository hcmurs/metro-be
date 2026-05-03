package com.example.cronjob.Controller;

import com.example.cronjob.DTO.Request.PayOSRequest;
import com.example.cronjob.DTO.Request.PayOSWebhookRequest;
import com.example.cronjob.DTO.Request.UpdateStatusOrder;
import com.example.cronjob.DTO.Response.ApiResponse;
import com.example.cronjob.Enum.OrderStatus;
import com.example.cronjob.Pojos.Orders;
import com.example.cronjob.Repository.OrdersRepository;
import com.example.cronjob.Service.OrdersService;
import com.example.cronjob.Service.PayOSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.payos.type.CheckoutResponseData;
import vn.payos.type.Webhook;

import java.net.URI;

@RestController
@RequestMapping("/api/payment/payos")
public class PayOSController {

    @Autowired
    private PayOSService payOSService;
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private  OrdersRepository ordersRepository;


    @PostMapping("/create")
    public ApiResponse<CheckoutResponseData> createPaymentLink(@RequestBody PayOSRequest payOSRequest) throws Exception {
        return ApiResponse.success(payOSService.createPaymentLink(payOSRequest));
    }
    @GetMapping("/success")
    public ResponseEntity<String> handleSuccess(
            @RequestParam("orderCode") Long orderCode,
            @RequestParam("code") String code,
            @RequestParam("desc") String desc
    ) {
        Orders order =ordersRepository.findByOrderId(orderCode);
        if (order == null) {
            return ResponseEntity.badRequest().body("Order not found");
        }

        if ("00".equals(code)) {
            ordersService.updateTransactionSuccess(orderCode);
        } else {
            ordersService.updateTransactionFailed(orderCode);
        }

        ordersRepository.save(order);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create("org.com.hcmurs://callback?result=success"))
                .build();
    }

    @GetMapping("/cancel")
    public ResponseEntity<?> handleCancel(@RequestParam("orderCode") Long orderCode) {
        Orders order = ordersRepository.findByOrderId(orderCode);
        if (order != null) {
            ordersService.updateTransactionFailed(orderCode);
        }
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create("org.com.hcmurs://callback?result=cancel"))
                .build();
   }


    @PostMapping("/update-status")
    public ResponseEntity<?> updateOrderStatus(@RequestBody UpdateStatusOrder request) {
        Orders order = ordersRepository.findByOrderId(request.getOrderCode());

        if (order == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
        }

//        order.setStatus(request.getStatus());
//        ordersRepository.save(order);
        if( request.getStatus()== OrderStatus.SUCCESSFUL) {
            ordersService.updateTransactionSuccess(request.getOrderCode());
        } else if (request.getStatus()== OrderStatus.FAILED) {
            ordersService.updateTransactionFailed(request.getOrderCode());
        }
        return ResponseEntity.ok("Order status updated");
    }

}
