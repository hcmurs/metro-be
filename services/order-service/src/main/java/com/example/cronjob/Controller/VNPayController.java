package com.example.cronjob.Controller;

import com.example.cronjob.Config.VNPayConfig;
import com.example.cronjob.DTO.Response.ApiResponse;
import com.example.cronjob.Service.OrdersService;
import com.example.cronjob.Service.VNPayService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class VNPayController {
    @Autowired
    private VNPayService vnPayService;

    @Autowired
    private OrdersService ordersService;

    private final VNPayConfig vnPayConfig;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public ApiResponse<Map<String, Object>> createPayment(
            HttpServletRequest req,
            @RequestParam(value = "orderInfo", defaultValue = "") Long orderId
    ) throws UnsupportedEncodingException {
        return vnPayService.createPaymentUrl(req, orderId);
    }

    // Callback method cũng cần sửa để đảm bảo consistency
    @GetMapping("/callback")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public ApiResponse<Map<String, Object>> paymentCallback(HttpServletRequest req) {
        return vnPayService.paymentCallback(req);
    }
}