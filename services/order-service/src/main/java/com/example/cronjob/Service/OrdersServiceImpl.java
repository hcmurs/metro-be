package com.example.cronjob.Service;

import com.example.cronjob.DTO.Request.OrderTicketDaysRequest;
import com.example.cronjob.DTO.Request.OrderTicketSingleRequest;
import com.example.cronjob.DTO.Response.ApiResponse;
import com.example.cronjob.DTO.Response.OrderResponse;
import com.example.cronjob.DTO.Response.TicketResponse;
import com.example.cronjob.DTO.Response.TransactionResponse;
import com.example.cronjob.Enum.OrderStatus;
import com.example.cronjob.Enum.TicketStatus;
import com.example.cronjob.Enum.TransactionStatus;
import com.example.cronjob.Mapping.OrderMapping;
import com.example.cronjob.Mapping.TransactionMapping;
import com.example.cronjob.Pojos.Orders;
import com.example.cronjob.Pojos.PaymentMethod;
import com.example.cronjob.Pojos.Transactions;
import com.example.cronjob.Repository.OrdersRepository;
import com.example.cronjob.Repository.TransactionsRepository;
import com.example.cronjob.client.TicketClient;
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

    @Autowired
    private TicketClient ticketClient;

    @Override
    @Transactional
    public ApiResponse<OrderResponse> generateOrderTicketSingle(OrderTicketSingleRequest request) {
        // Get ticket using fareMatrixId
        ApiResponse<TicketResponse> ticketRes = ticketClient.createTicketFare(request.getFareMatrixId());
        TicketResponse ticketResponse = ticketRes.getData();
        if (ticketResponse == null) {
            throw new EntityNotFoundException("Create ticket failed for Fare Matrix ID: " + request.getFareMatrixId());
        }

        return generateOrder(request.getPaymentMethodId(), ticketResponse);
    }

    @Override
    public ApiResponse<OrderResponse> generateOrderTicketDays(OrderTicketDaysRequest request) {
        // Get ticket using ticketId
        ApiResponse<TicketResponse> ticketRes = ticketClient.createTicketType(request.getTicketId());
        TicketResponse ticketResponse = ticketRes.getData();
        if (ticketResponse == null) {
            throw new EntityNotFoundException("Create ticket failed for Ticket Type ID: " + request.getTicketId());
        }

        return generateOrder(request.getPaymentMethodId(), ticketResponse);
    }

    private ApiResponse<OrderResponse> generateOrder(Long paymentMethodId, TicketResponse ticketResponse) {
        // Validate payment method
        PaymentMethod paymentMethod = paymentMethodService.getPaymentMethodById(paymentMethodId);
        if (paymentMethod == null) {
            throw new EntityNotFoundException("Payment method not found with ID: " + paymentMethodId);
        }

        // Create order
        Orders orders = Orders.builder()
                // hiện tại t thaasy chưa có jwt token nên userId là măặc định mốt token thì sẽ lấy từ jwt
                .userId(1L)
                .ticketId(ticketResponse.id())
                .status(OrderStatus.PENDING)
                .amount(BigDecimal.valueOf(ticketResponse.actualPrice()))
                .createdAt(LocalDateTime.now())
                .build();
        Orders savedOrder = ordersRepository.save(orders);

        // Create transaction
        Transactions transactions = Transactions.builder()
                .userId(savedOrder.getUserId())
                .paymentMethod(paymentMethod)
                .transactionStatus(TransactionStatus.PENDING)
                .amount(savedOrder.getAmount())
                .createAt(LocalDateTime.now())
                .build();
        transactions.setOrder(savedOrder);

        Transactions savedTransaction = transactionsRepository.save(transactions);

        // Create response
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
            ticketClient.updateTicketStatus(orders.getTicketId(),TicketStatus.NOT_USED);
        } else if (orders.getTransaction().getTransactionStatus() == TransactionStatus.FAILED) {
            orders.setStatus(OrderStatus.FAILED);
            ticketClient.updateTicketStatus(orders.getTicketId(),TicketStatus.CANCELLED);
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
