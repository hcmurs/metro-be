package com.example.cronjob.Service;

import com.example.cronjob.DTO.Response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

public interface VNPayService {
    ApiResponse<Map<String, Object>> createPaymentUrl(HttpServletRequest req, Long orderId);
    ApiResponse<Map<String, Object>> paymentCallback(HttpServletRequest req);
}
