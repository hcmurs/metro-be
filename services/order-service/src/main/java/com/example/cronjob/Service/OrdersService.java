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

import com.example.cronjob.DTO.Request.OrderTicketDaysRequest;
import com.example.cronjob.DTO.Request.OrderTicketSingleRequest;
import com.example.cronjob.DTO.Response.ApiResponse;
import com.example.cronjob.DTO.Response.OrderResponse;
import com.example.cronjob.DTO.Response.TransactionResponse;
import com.example.cronjob.Enum.TicketStatus;
import java.util.List;

public interface OrdersService {
  ApiResponse<OrderResponse> generateOrderTicketSingle(
      OrderTicketSingleRequest orderTicketSingleRequest, String token);

  ApiResponse<OrderResponse> generateOrderTicketDays(
      OrderTicketDaysRequest orderTicketSingleRequest, String token);

  ApiResponse<List<OrderResponse>> getAllOrders();

  ApiResponse<OrderResponse> getOrderById(Long orderId);

  ApiResponse<OrderResponse> updateOrder(Long orderId);

  ApiResponse<TransactionResponse> updateTransactionSuccess(Long orderId);

  ApiResponse<TransactionResponse> updateTransactionFailed(Long orderId);

  ApiResponse<List<OrderResponse>> getOrderByUserId(String token);

  ApiResponse<List<OrderResponse.OrderDetailResponse>> getOrderDetailByUserId(String token);

  ApiResponse<List<OrderResponse.OrderDetailResponse>> getOrderDetailByStatus(
      String token, TicketStatus status);
}
