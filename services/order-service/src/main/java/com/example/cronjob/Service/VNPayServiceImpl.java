/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: Order-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package com.example.cronjob.Service;

import com.example.cronjob.Config.VNPayConfig;
import com.example.cronjob.DTO.Response.ApiResponse;
import com.example.cronjob.Enum.OrderStatus;
import com.example.cronjob.Pojos.Orders;
import com.example.cronjob.Repository.OrdersRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class VNPayServiceImpl implements VNPayService {
    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private TicketClient ticketClient;

    @Autowired
    private TransactionsRepository transactionsRepository;

    @Autowired
    private PaymentMethodService paymentMethodService;

    @Override
    public ApiResponse<Map<String, Object>> createPaymentUrl(HttpServletRequest req, Long orderId) {
        Orders order = ordersRepository.findByOrderId(orderId);
        if (order == null) {
            throw new EntityNotFoundException("Order not found with ID: " + orderId);
        }
        if(order.getStatus()!= OrderStatus.PENDING) {
            return ApiResponse.<Map<String, Object>>builder()
                    .status(400)
                    .message("Order is not in pending status")
                    .data(null)
                    .build();
        }
        String vnp_TxnRef = VNPayConfig.getRandomNumber(8);
        String vnp_IpAddr = VNPayConfig.getRealIpAddress(req);
        String vnp_TmnCode = VNPayConfig.vnp_TmnCode;

        long vnpAmount = order.getAmount().longValue() * 100;
        Orders orderInfo =ordersRepository.findByOrderId(orderId);
        if (orderInfo == null) {
            throw new EntityNotFoundException("Order not found with ID: " + orderId);
        }

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", VNPayConfig.vnp_Version);
        vnp_Params.put("vnp_Command", VNPayConfig.vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_BankCode", "NCB");
        vnp_Params.put("vnp_Amount", String.valueOf(vnpAmount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", String.valueOf(orderInfo.getOrderId()));
        vnp_Params.put("vnp_OrderType", "other");
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", VNPayConfig.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        // Set Vietnam timezone
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        formatter.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));

        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        // CRITICAL: Sort parameters alphabetically for hash calculation
        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);

        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();

        Iterator<String> itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = itr.next();
            String fieldValue = vnp_Params.get(fieldName);
            if (fieldValue != null && !fieldValue.isEmpty()) {
                hashData.append(URLEncoder.encode(fieldName, StandardCharsets.UTF_8));
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.UTF_8));

                query.append(URLEncoder.encode(fieldName, StandardCharsets.UTF_8));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.UTF_8));

                if (itr.hasNext()) {
                    hashData.append('&');
                    query.append('&');
                }
            }
        }

        // Create signature
        String vnp_SecureHash = VNPayConfig.hmacSHA512(VNPayConfig.vnp_SecretKey, hashData.toString());
        query.append("&vnp_SecureHash=").append(URLEncoder.encode(vnp_SecureHash, StandardCharsets.UTF_8));

        String paymentUrl = VNPayConfig.vnp_PayUrl + "?" + query.toString();
        System.out.println("PAYMENT URL: " + paymentUrl);

        Map<String, Object> result = new HashMap<>();
        result.put("paymentUrl", paymentUrl);
        result.put("transactionRef", vnp_TxnRef);
        result.put("amount", order.getAmount().longValue());

        return ApiResponse.<Map<String, Object>>builder()
                .status(200)
                .message("Payment URL created successfully")
                .data(result)
                .build();
    }

    @Override
    public ApiResponse<Map<String, Object>> paymentCallback(HttpServletRequest req) {

        Map<String, String> vnp_Params = new HashMap<>();

        // Get all parameters from VNPay callback
        Map<String, String[]> requestParams = req.getParameterMap();
        for (String paramName : requestParams.keySet()) {
            String paramValue = req.getParameter(paramName);
            if (paramValue != null && !paramValue.isEmpty()) {
                vnp_Params.put(paramName, paramValue);
                System.out.println(paramName + ": " + paramValue);
            }
        }

        // Get secure hash from VNPay
        String vnp_SecureHash = vnp_Params.get("vnp_SecureHash");
        vnp_Params.remove("vnp_SecureHash");
        vnp_Params.remove("vnp_SecureHashType");

        // Recreate signature using same method as create payment
        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);

        StringBuilder hashData = new StringBuilder();
        Iterator<String> itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = itr.next();
            String fieldValue = vnp_Params.get(fieldName);
            if (fieldValue != null && !fieldValue.isEmpty()) {
                hashData.append(URLEncoder.encode(fieldName, StandardCharsets.UTF_8));
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.UTF_8));

                if (itr.hasNext()) {
                    hashData.append('&');
                }
            }
        }

        String signValue = VNPayConfig.hmacSHA512(VNPayConfig.vnp_SecretKey, hashData.toString());

        Map<String, Object> result = new HashMap<>();

        if (signValue.equals(vnp_SecureHash)) {
            String vnp_ResponseCode = vnp_Params.get("vnp_ResponseCode");
            String vnp_TransactionStatus = vnp_Params.get("vnp_TransactionStatus");
            String vnp_TxnRef = vnp_Params.get("vnp_TxnRef");
            String vnp_Amount = vnp_Params.get("vnp_Amount");

            if ("00".equals(vnp_ResponseCode) && "00".equals(vnp_TransactionStatus)) {
                result.put("status", "success");
                result.put("message", "Payment completed successfully");
                result.put("transactionId", vnp_TxnRef);
                result.put("amount", Long.parseLong(vnp_Amount) / 100);
                result.put("responseCode", vnp_ResponseCode);
                result.put("transactionStatus", vnp_TransactionStatus);
                result.put("paymentTime", vnp_Params.get("vnp_PayDate"));
                result.put("orderInfo", vnp_Params.get("vnp_OrderInfo"));
                ordersService.updateTransactionSuccess(Long.parseLong(vnp_Params.get("vnp_OrderInfo")));
            } else {
                result.put("status", "failed");
                result.put("message", "Payment failed");
                result.put("transactionId", vnp_TxnRef);
                result.put("responseCode", vnp_ResponseCode);
                result.put("orderInfo", vnp_Params.get("vnp_OrderInfo"));
                result.put("transactionStatus", vnp_TransactionStatus);
                ordersService.updateTransactionFailed(Long.parseLong(vnp_Params.get("vnp_OrderInfo")));

            }
        } else {
            result.put("status", "invalid");
            result.put("message", "Invalid signature");

        }

        return ApiResponse.<Map<String, Object>>builder()
                .status(200)
                .message("Callback processed")
                .data(result)
                .build();
    }

    @Override
    public ApiResponse<Map<String, Object>> createUrlUpgradeTicket(HttpServletRequest req,Long orderId,Long endStationId) {
        String vnp_TxnRef = VNPayConfig.getRandomNumber(8);
        String vnp_IpAddr = VNPayConfig.getRealIpAddress(req);
        String vnp_TmnCode = VNPayConfig.vnp_TmnCode;
        Orders order = ordersRepository.findByOrderId(orderId);
        if (order == null) {
            throw new EntityNotFoundException("Order not found with ID: " + orderId);
        }
        Long ticketId = order.getTicketId();
        ApiResponse<TicketResponse> ticketResponse = ticketClient.getTicketById(ticketId);
        if(ticketResponse.getStatus()!=200) {
            throw new IllegalArgumentException(ticketResponse.getMessage());
        }
        TicketResponse ticket = ticketResponse.getData();
        ApiResponse<Double> upgradeAmountResponse = ticketClient.getUpgradeAmount(ticketId, endStationId);
        if(upgradeAmountResponse.getStatus()!=200) {
            throw new IllegalArgumentException(ticketResponse.getMessage());
        }
        long vnpAmount =  upgradeAmountResponse.getData().longValue() * 100;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", VNPayConfig.vnp_Version);
        vnp_Params.put("vnp_Command", VNPayConfig.vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_BankCode", "NCB");
        vnp_Params.put("vnp_Amount", String.valueOf(vnpAmount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_OrderType", "other");
        vnp_Params.put("vnp_ReturnUrl", VNPayConfig.vnp_ReturnUrl);
        vnp_Params.put("vnp_OrderInfo", orderId +"~"+ticketId+"~"+endStationId+"~upgrade");
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);



        // Set Vietnam timezone
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        formatter.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));

        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        // CRITICAL: Sort parameters alphabetically for hash calculation
        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);

        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();

        Iterator<String> itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = itr.next();
            String fieldValue = vnp_Params.get(fieldName);
            if (fieldValue != null && !fieldValue.isEmpty()) {
                hashData.append(URLEncoder.encode(fieldName, StandardCharsets.UTF_8));
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.UTF_8));

                query.append(URLEncoder.encode(fieldName, StandardCharsets.UTF_8));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.UTF_8));

                if (itr.hasNext()) {
                    hashData.append('&');
                    query.append('&');
                }
            }
        }

        // Create signature
        String vnp_SecureHash = VNPayConfig.hmacSHA512(VNPayConfig.vnp_SecretKey, hashData.toString());
        query.append("&vnp_SecureHash=").append(URLEncoder.encode(vnp_SecureHash, StandardCharsets.UTF_8));

        String paymentUrl = VNPayConfig.vnp_PayUrl + "?" + query.toString();
        System.out.println("PAYMENT URL: " + paymentUrl);

        Map<String, Object> result = new HashMap<>();
        result.put("paymentUrl", paymentUrl);
        result.put("transactionRef", vnp_TxnRef);
        result.put("amount", upgradeAmountResponse.getData().longValue());

        return ApiResponse.<Map<String, Object>>builder()
                .status(200)
                .message("Payment URL created successfully")
                .data(result)
                .build();
    }

    @Override
    public ApiResponse<Map<String, Object>> paymentCallbackUpgradeTicket(HttpServletRequest req) {
        Map<String, String> vnp_Params = new HashMap<>();

        // Get all parameters from VNPay callback
        Map<String, String[]> requestParams = req.getParameterMap();
        for (String paramName : requestParams.keySet()) {
            String paramValue = req.getParameter(paramName);
            if (paramValue != null && !paramValue.isEmpty()) {
                vnp_Params.put(paramName, paramValue);
                System.out.println(paramName + ": " + paramValue);
            }
        }

        // Get secure hash from VNPay
        String vnp_SecureHash = vnp_Params.get("vnp_SecureHash");
        vnp_Params.remove("vnp_SecureHash");
        vnp_Params.remove("vnp_SecureHashType");

        // Recreate signature using same method as create payment
        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);

        StringBuilder hashData = new StringBuilder();
        Iterator<String> itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = itr.next();
            String fieldValue = vnp_Params.get(fieldName);
            if (fieldValue != null && !fieldValue.isEmpty()) {
                hashData.append(URLEncoder.encode(fieldName, StandardCharsets.UTF_8));
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.UTF_8));

                if (itr.hasNext()) {
                    hashData.append('&');
                }
            }
        }

        String signValue = VNPayConfig.hmacSHA512(VNPayConfig.vnp_SecretKey, hashData.toString());

        Map<String, Object> result = new HashMap<>();

        if (signValue.equals(vnp_SecureHash)) {
            String vnp_ResponseCode = vnp_Params.get("vnp_ResponseCode");
            String vnp_TransactionStatus = vnp_Params.get("vnp_TransactionStatus");
            String vnp_TxnRef = vnp_Params.get("vnp_TxnRef");
            String vnp_Amount = vnp_Params.get("vnp_Amount");
            String info = vnp_Params.get("vnp_OrderInfo");
            String[] infos = info.split("~");
            String orderId = infos[0];
            String ticketId = infos[1];
            String endStationId = infos[2];

            if ("00".equals(vnp_ResponseCode) && "00".equals(vnp_TransactionStatus)) {
                result.put("status", "success");
                result.put("message", "Payment completed successfully");
                result.put("transactionId", vnp_TxnRef);
                result.put("amount", Long.parseLong(vnp_Amount) / 100);
                result.put("responseCode", vnp_ResponseCode);
                result.put("transactionStatus", vnp_TransactionStatus);
                result.put("paymentTime", vnp_Params.get("vnp_PayDate"));
                result.put("orderInfo", vnp_Params.get("vnp_OrderInfo"));
                ticketClient.upgradeTicket(Long.parseLong(ticketId), Long.parseLong(endStationId));
//                Transactions transaction = new Transactions();
//                transaction.setTransactionId(Long.parseLong(vnp_TxnRef));
//                transaction.setOrder(ordersRepository.findByOrderId(Long.parseLong(orderId)));
//                transaction.setAmount(new BigDecimal(Long.parseLong(vnp_Amount) / 100.0));
//                transaction.setTransactionStatus(TransactionStatus.SUCCESSFUL);
//                transaction.setPaymentMethod(paymentMethodService.getPaymentMethodById(1L));
//                transactionsRepository.save(transaction);
            } else {
                result.put("status", "failed");
                result.put("message", "Payment failed");
                result.put("transactionId", vnp_TxnRef);
                result.put("responseCode", vnp_ResponseCode);
                result.put("vnp_TicketId", vnp_Params.get("vnp_TicketId"));
                result.put("vnp_EndStationId", vnp_Params.get("vnp_EndStationId"));
                result.put("transactionStatus", vnp_TransactionStatus);
            }
        } else {
            result.put("status", "invalid");
            result.put("message", "Invalid signature");

        }

        return ApiResponse.<Map<String, Object>>builder()
                .status(200)
                .message("Callback processed")
                .data(result)
                .build();
    }


}

