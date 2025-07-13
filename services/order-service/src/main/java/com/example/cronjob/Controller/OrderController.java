package com.example.cronjob.Controller;

import com.example.cronjob.DTO.Request.OrderTicketDaysRequest;
import com.example.cronjob.DTO.Request.OrderTicketSingleRequest;
import com.example.cronjob.DTO.Response.ApiResponse;
import com.example.cronjob.DTO.Response.OrderResponse;
import com.example.cronjob.DTO.Response.TransactionResponse;
import com.example.cronjob.Enum.TicketStatus;
import com.example.cronjob.Service.OrdersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@Tag(name = "Order Management", description = "APIs for managing user orders")
public class OrderController {
    @Autowired
    private OrdersService ordersService;

    @PostMapping("/create/single")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @Operation(summary = "Create a new order", description = "Creates a new order for a ticket single.")
    public ApiResponse<OrderResponse> createOrderSingle(@RequestBody OrderTicketSingleRequest orderTicketSingleRequest,
                                                        @RequestHeader("Authorization") String token) {
        String validatedToken = token.startsWith("Bearer ") ? token.substring(7) : token;
        // Logic to create an order
        return ordersService.generateOrderTicketSingle(orderTicketSingleRequest, validatedToken);
    }

    @PostMapping("/create/days")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @Operation(summary = "Create a new order", description = "Creates a new order for a ticket days.")
    public ApiResponse<OrderResponse> createOrder(@RequestBody OrderTicketDaysRequest orderTicketDaysRequest,
                                                  @RequestHeader("Authorization") String token) {
        // Logic to create an order
        String validatedToken = token.startsWith("Bearer ") ? token.substring(7) : token;
        return ordersService.generateOrderTicketDays(orderTicketDaysRequest, validatedToken);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Get all orders", description = "Retrieves all orders for the authenticated user.")
    public ApiResponse<List<OrderResponse>> getAllOrders(@RequestHeader ("Authorization") String token) {
        return ordersService.getAllOrders();
    }

    @GetMapping("/{orderId}")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER') or hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Get order by ID", description = "Retrieves an order by its ID for the authenticated user.")
    public ApiResponse<OrderResponse> getOrderById(@PathVariable Long orderId) {
        return ordersService.getOrderById(orderId);
    }

    @GetMapping("/user")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @Operation(summary = "Get order by user ID", description = "Retrieves an order by the user's ID.")
    public ApiResponse<List<OrderResponse>> getOrderByUserId(@RequestHeader ("Authorization") String token) {
        String validatedToken = token.startsWith("Bearer ") ? token.substring(7) : token;
        return ordersService.getOrderByUserId(validatedToken);
    }

    @GetMapping("/user/details")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @Operation(summary = "Get order by user ID", description = "Retrieves an order by the user's ID.")
    public ApiResponse<List<OrderResponse.OrderDetailResponse>> getOrderDetailByUserId(@RequestHeader ("Authorization") String token) {
        String validatedToken = token.startsWith("Bearer ") ? token.substring(7) : token;
        return ordersService.getOrderDetailByUserId(validatedToken);
    }

    @PutMapping("update-success/{orderId}")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @Operation(summary = "Update an order", description = "Updates the status of an existing order by its ID.")
    public ApiResponse<TransactionResponse> updateTransactionSuccess(@PathVariable Long orderId) {
        return ordersService.updateTransactionSuccess(orderId);
    }

    @PutMapping("update-fail/{orderId}")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @Operation(summary = "Update an order", description = "Updates the status of an existing order by its ID.")
    public ApiResponse<TransactionResponse> updateTransactionFailed(@PathVariable Long orderId) {
        return ordersService.updateTransactionFailed(orderId);
    }

    @PutMapping("update-order/{orderId}")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @Operation(summary = "Update transaction for an order", description = "Updates the transaction details for an existing order by its ID.")
    public ApiResponse<OrderResponse> updateTransaction(@PathVariable Long orderId) {
        return ordersService.updateOrder(orderId);
    }

    @GetMapping("/user/details/status")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @Operation(summary = "Get order details by status", description = "Retrieves order details by status for the authenticated user.")
    public ApiResponse<List<OrderResponse.OrderDetailResponse>> getOrderDetailByStatus(
            @RequestHeader("Authorization") String token,
            @RequestParam("status") String status) {
        String validatedToken = token.startsWith("Bearer ") ? token.substring(7) : token;
        return ordersService.getOrderDetailByStatus(validatedToken, TicketStatus.valueOf(status));
    }

}
