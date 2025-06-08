package com.example.cronjob.Service;

import com.example.cronjob.DTO.Request.OrderRequest;
import com.example.cronjob.DTO.Response.ApiResponse;
import com.example.cronjob.DTO.Response.OrderResponse;
import com.example.cronjob.DTO.Response.TransactionResponse;
import org.springframework.stereotype.Service;

import java.util.List;


public interface OrdersService {
    ApiResponse<OrderResponse> generateOrders(OrderRequest orderRequest);
    ApiResponse<List<OrderResponse>> getAllOrders();
    ApiResponse<OrderResponse> getOrderById(Long orderId);
    ApiResponse<OrderResponse> updateOrder(Long orderId);
    ApiResponse<TransactionResponse> updateTransaction(Long orderId);

}
