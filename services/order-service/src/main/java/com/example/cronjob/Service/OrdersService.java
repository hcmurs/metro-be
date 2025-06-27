package com.example.cronjob.Service;

import com.example.cronjob.DTO.Request.OrderTicketDaysRequest;
import com.example.cronjob.DTO.Request.OrderTicketSingleRequest;
import com.example.cronjob.DTO.Response.ApiResponse;
import com.example.cronjob.DTO.Response.OrderResponse;
import com.example.cronjob.DTO.Response.TransactionResponse;
import com.example.cronjob.Enum.TicketStatus;

import java.util.List;


public interface OrdersService {
    ApiResponse<OrderResponse> generateOrderTicketSingle(OrderTicketSingleRequest orderTicketSingleRequest,String token);
    ApiResponse<OrderResponse> generateOrderTicketDays(OrderTicketDaysRequest orderTicketSingleRequest,String token);
    ApiResponse<List<OrderResponse>> getAllOrders();
    ApiResponse<OrderResponse> getOrderById(Long orderId);
    ApiResponse<OrderResponse> updateOrder(Long orderId);
    ApiResponse<TransactionResponse> updateTransactionSuccess(Long orderId);
    ApiResponse<TransactionResponse> updateTransactionFailed(Long orderId);
    ApiResponse<List<OrderResponse>> getOrderByUserId(String token);
    ApiResponse<List<OrderResponse.OrderDetailResponse>> getOrderDetailByUserId(String token);
    ApiResponse<List<OrderResponse.OrderDetailResponse>> getOrderDetailByStatus(String token, TicketStatus status);
}
