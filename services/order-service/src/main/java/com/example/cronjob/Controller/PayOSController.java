package com.example.cronjob.Controller;

import com.example.cronjob.DTO.Request.PayOSRequest;
import com.example.cronjob.DTO.Response.ApiResponse;
import com.example.cronjob.Service.PayOSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.payos.type.CheckoutResponseData;

@RestController
@RequestMapping("/api/payment/payos")
public class PayOSController {

    @Autowired
    private PayOSService payOSService;


    @PostMapping("/create")
    public ApiResponse<CheckoutResponseData> createPaymentLink(@RequestBody PayOSRequest payOSRequest) throws Exception {
        return ApiResponse.success(payOSService.createPaymentLink(payOSRequest));
    }

}
