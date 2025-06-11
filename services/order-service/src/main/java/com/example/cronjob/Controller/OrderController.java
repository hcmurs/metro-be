package com.example.cronjob.Controller;

import com.example.cronjob.DTO.Request.OrderTicketDaysRequest;
import com.example.cronjob.DTO.Request.OrderTicketSingleRequest;
import com.example.cronjob.DTO.Response.ApiResponse;
import com.example.cronjob.DTO.Response.OrderResponse;
import com.example.cronjob.DTO.Response.TransactionResponse;
import com.example.cronjob.Service.OrdersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/orders")
@Tag(name = "Order Management", description = "APIs for managing user orders")
public class OrderController {
    @Autowired
    private OrdersService ordersService;

    @PostMapping("/create/single")
    @Operation(summary = "Create a new order", description = "Creates a new order for a ticket single.")
    public ApiResponse<OrderResponse> createOrderSingle(@RequestBody OrderTicketSingleRequest orderTicketSingleRequest) {
        // Logic to create an order
        return ordersService.generateOrderTicketSingle(orderTicketSingleRequest);
    }

    @PostMapping("/create/days")
    @Operation(summary = "Create a new order", description = "Creates a new order for a ticket days.")
    public ApiResponse<OrderResponse> createOrder(@RequestBody OrderTicketDaysRequest orderTicketDaysRequest) {
        // Logic to create an order
        return ordersService.generateOrderTicketDays(orderTicketDaysRequest);
    }

    @GetMapping("/all")
    @Operation(summary = "Get all orders", description = "Retrieves all orders for the authenticated user.")
    public ApiResponse<List<OrderResponse>> getAllOrders() {
        return ordersService.getAllOrders();
    }

    @GetMapping("/{orderId}")
    @Operation(summary = "Get order by ID", description = "Retrieves an order by its ID for the authenticated user.")
    public ApiResponse<OrderResponse> getOrderById(@PathVariable Long orderId) {
        return ordersService.getOrderById(orderId);
    }

    @PutMapping("update/{orderId}")
    @Operation(summary = "Update an order", description = "Updates the status of an existing order by its ID.")
    public ApiResponse<OrderResponse> updateOrder(@PathVariable Long orderId) {
        return ordersService.updateOrder(orderId);
    }

    @PutMapping("update-transaction/{orderId}")
    @Operation(summary = "Update transaction for an order", description = "Updates the transaction details for an existing order by its ID.")
    public ApiResponse<TransactionResponse> updateTransaction(@PathVariable Long orderId) {
        return ordersService.updateTransaction(orderId);
    }

}
