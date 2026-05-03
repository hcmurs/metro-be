/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: Order-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package com.example.cronjob.Controller;

import com.example.cronjob.Config.VNPayConfig;
import com.example.cronjob.DTO.Response.ApiResponse;
import com.example.cronjob.Service.OrdersService;
import com.example.cronjob.Service.VNPayService;
import jakarta.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.*;
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

    @PostMapping("/upgrade")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public ApiResponse<Map<String, Object>> createPaymentUpgradeTicket(
            HttpServletRequest req,
            @RequestParam(value = "ticketId", defaultValue = "") Long ticketId,
            @RequestParam(value = "endStationId", defaultValue = "") Long endStationId
    ) throws UnsupportedEncodingException {
        return vnPayService.createUrlUpgradeTicket(req, ticketId, endStationId);
    }

    @GetMapping("/callback/upgrade")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public ApiResponse<Map<String, Object>> paymentCallbackUpgrade(HttpServletRequest req) {
        return vnPayService.paymentCallbackUpgradeTicket(req);
    }
}
