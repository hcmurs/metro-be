package com.example.cronjob.Service;

import com.example.cronjob.DTO.Request.OrderRequest;
import com.example.cronjob.DTO.Response.ApiResponse;
import com.example.cronjob.DTO.Response.OrderResponse;
import com.example.cronjob.DTO.Response.TransactionResponse;
import com.example.cronjob.Enum.OrderStatus;
import com.example.cronjob.Enum.TransactionStatus;
import com.example.cronjob.Mapping.OrderMapping;
import com.example.cronjob.Mapping.TransactionMapping;
import com.example.cronjob.Pojos.Orders;
import com.example.cronjob.Pojos.PaymentMethod;
import com.example.cronjob.Pojos.Transactions;
import com.example.cronjob.Repository.OrdersRepository;
import com.example.cronjob.Repository.TransactionsRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class OrdersServiceImpl implements OrdersService {
    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private TransactionsRepository transactionsRepository;

    @Autowired
    private PaymentMethodService paymentMethodService;

    @Autowired
    private TransactionMapping transactionMapping;

    @Autowired
    private OrderMapping orderMapping;

    @Override
    @Transactional
    public ApiResponse<OrderResponse> generateOrders(OrderRequest orderRequest) {
        PaymentMethod paymentMethod = paymentMethodService.getPaymentMethodById(orderRequest.getPaymentMethodId());
        if (paymentMethod == null) {
            throw new EntityNotFoundException("Payment method not found with ID: " + orderRequest.getPaymentMethodId());
        }

        Orders orders = Orders.builder()
                .userId(orderRequest.getUserId())
                .ticketId(orderRequest.getTicketId())
                .status(OrderStatus.PENDING)
                .amount(new BigDecimal("100.00"))
                .createdAt(LocalDateTime.now())
                .build();
        Orders savedOrder = ordersRepository.save(orders);

        Transactions transactions = Transactions.builder()
                .userId(savedOrder.getUserId())
                .paymentMethod(paymentMethod)
                .transactionStatus(TransactionStatus.PENDING)
                .amount(savedOrder.getAmount())
                .createAt(LocalDateTime.now())
                .build();
        transactions.setOrder(savedOrder);

        Transactions savedTransaction = transactionsRepository.save(transactions);
        OrderResponse orderResponse = OrderResponse.builder()
                .orderId(savedOrder.getOrderId())
                .userId(savedOrder.getUserId())
                .ticketId(savedOrder.getTicketId())
                .status(savedOrder.getStatus())
                .amount(savedOrder.getAmount())
                .createdAt(savedOrder.getCreatedAt())
                .transaction(transactionMapping.toResponse(savedTransaction))
                .build();

        return ApiResponse.<OrderResponse>builder()
                .status(200)
                .message("Order created successfully")
                .data(orderResponse)
                .build();
    }

    @Override
    public ApiResponse<List<OrderResponse>> getAllOrders() {
        return ApiResponse.<List<OrderResponse>>builder()
                .status(200)
                .message("All orders retrieved successfully")
                .data(orderMapping.toResponseList(ordersRepository.findAll()))
                .build();
    }

    @Override
    public ApiResponse<OrderResponse> getOrderById(Long orderId) {
        if (!ordersRepository.existsByOrderId(orderId)) {
            throw new EntityNotFoundException("Order not found with ID: " + orderId);
        }
        return ApiResponse.<OrderResponse>builder()
                .status(200)
                .message("Order retrieved successfully")
                .data(orderMapping.toResponse(ordersRepository.findByOrderId(orderId)))
                .build();
    }

    @Override
    public ApiResponse<OrderResponse> updateOrder(Long orderId) {
        Orders orders = ordersRepository.findByOrderId(orderId);
        if (orders == null) {
            throw new EntityNotFoundException("Order not found with ID: " + orderId);
        }
        if (orders.getTransaction().getTransactionStatus() == TransactionStatus.SUCCESSFUL) {
            orders.setStatus(OrderStatus.SUCCESSFUL);
        } else if (orders.getTransaction().getTransactionStatus() == TransactionStatus.FAILED) {
            orders.setStatus(OrderStatus.FAILED);
        } else {
            orders.setStatus(OrderStatus.PENDING);
        }
        orders.setUpdatedAt(LocalDateTime.now());
        Orders updatedOrder = ordersRepository.save(orders);
        OrderResponse orderResponse = orderMapping.toResponse(updatedOrder);
        return ApiResponse.<OrderResponse>builder()
                .status(200)
                .message("Order updated successfully")
                .data(orderResponse)
                .build();
    }

    @Override
    public ApiResponse<TransactionResponse> updateTransaction(Long orderId) {
        Orders orders = ordersRepository.findByOrderId(orderId);
        return ApiResponse.<TransactionResponse>builder()
                .status(501)
                .message("Not implemented yet")
                .data(null)
                .build();
    }
}
