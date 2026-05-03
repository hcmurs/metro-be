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

import com.example.cronjob.Config.JwtUtil;
import com.example.cronjob.Config.RabbitMQConfig;
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
import com.example.cronjob.event.OrderCompletedEvent;
import feign.FeignException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OrdersServiceImpl implements OrdersService {
  private final OrdersRepository ordersRepository;

  private final TransactionsRepository transactionsRepository;

  private final PaymentMethodService paymentMethodService;

  private final TransactionMapping transactionMapping;

  private final OrderMapping orderMapping;

  private final TicketClient ticketClient;

  private final JwtUtil jwtUtil;

  private final RabbitTemplate rabbitTemplate;

  @Override
  @Transactional
  public ApiResponse<OrderResponse> generateOrderTicketSingle(
      OrderTicketSingleRequest request, String token) {
    // Get ticket using fareMatrixId
    ApiResponse<TicketResponse> ticketRes =
        ticketClient.createTicketFare(request.getFareMatrixId());
    TicketResponse ticketResponse = ticketRes.getData();
    if (ticketResponse == null) {
      throw new EntityNotFoundException(ticketRes.getMessage());
    }

    return generateOrder(request.getPaymentMethodId(), ticketResponse, token);
  }

  @Override
  public ApiResponse<OrderResponse> generateOrderTicketDays(
      OrderTicketDaysRequest request, String token) {
    // Get ticket using ticketId
    try {
      ApiResponse<TicketResponse> ticketRes = ticketClient.createTicketType(request.getTicketId());
      TicketResponse ticketResponse = ticketRes.getData();
      if (ticketResponse == null) {
        throw new EntityNotFoundException(ticketRes.getMessage());
      }

      return generateOrder(request.getPaymentMethodId(), ticketResponse, token);
    } catch (FeignException.Forbidden ex) {
      throw new IllegalArgumentException("This ticket only available for students");
    }
  }

  private ApiResponse<OrderResponse> generateOrder(
      Long paymentMethodId, TicketResponse ticketResponse, String token) {
    // Validate payment method
    PaymentMethod paymentMethod = paymentMethodService.getPaymentMethodById(paymentMethodId);
    if (paymentMethod == null) {
      throw new EntityNotFoundException("Payment method not found with ID: " + paymentMethodId);
    }
    Long userId = jwtUtil.extractUserId(token);

    // Create order
    Orders orders =
        Orders.builder()
            // hiện tại t thaasy chưa có jwt token nên userId là măặc định mốt token thì sẽ lấy từ
            // jwt
            .userId(userId)
            .ticketId(ticketResponse.id())
            .status(OrderStatus.PENDING)
            .amount(BigDecimal.valueOf(ticketResponse.actualPrice()))
            .createdAt(LocalDateTime.now())
            .stripeSessionId(null)
            .build();
    Orders savedOrder = ordersRepository.save(orders);

    // Create transaction
    Transactions transactions =
        Transactions.builder()
            .userId(savedOrder.getUserId())
            .paymentMethod(paymentMethod)
            .transactionStatus(TransactionStatus.PENDING)
            .amount(savedOrder.getAmount())
            .createAt(LocalDateTime.now())
            .build();
    transactions.setOrder(savedOrder);

    Transactions savedTransaction = transactionsRepository.save(transactions);

    // Create response
    OrderResponse orderResponse =
        OrderResponse.builder()
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
    String name = "";
    float price = 0;
    String type = "";
    String token =
        SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
    log.info(token);
    if (orders == null) {
      throw new EntityNotFoundException("Order not found with ID: " + orderId);
    }
    if (orders.getTransaction().getTransactionStatus() == TransactionStatus.SUCCESSFUL) {
      orders.setStatus(OrderStatus.SUCCESSFUL);
      ApiResponse<TicketResponse> res =
          ticketClient.updateTicketStatus(orders.getTicketId(), TicketStatus.NOT_USED);
      if (res.getData() == null) {
        throw new EntityNotFoundException(res.getMessage());
      }
      type = "Buy ticket success";
      name = res.getData().name();
      price = res.getData().actualPrice();
    } else if (orders.getTransaction().getTransactionStatus() == TransactionStatus.FAILED) {
      orders.setStatus(OrderStatus.FAILED);
      ApiResponse<TicketResponse> res =
          ticketClient.updateTicketStatus(orders.getTicketId(), TicketStatus.CANCELLED);
      if (res.getData() == null) {
        throw new EntityNotFoundException(res.getMessage());
      }
      type = "Buy ticket failed";
      name = res.getData().name();
      price = res.getData().actualPrice();
    } else {
      orders.setStatus(OrderStatus.PENDING);
    }
    OrderCompletedEvent event =
        new OrderCompletedEvent(
            orders.getOrderId(), jwtUtil.extractEmail(token), type, name, price);
    rabbitTemplate.convertAndSend(
        RabbitMQConfig.ORDER_EXCHANGE, RabbitMQConfig.ORDER_COMPLETED_ROUTING_KEY, event);
    log.info("Order completed successfully event sent to RabbitMQ : {}", event);
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
  public ApiResponse<TransactionResponse> updateTransactionSuccess(Long orderId) {
    Orders orders = ordersRepository.findByOrderId(orderId);
    if (orders == null) {
      throw new EntityNotFoundException("Order not found with ID: " + orderId);
    }
    Transactions transaction = orders.getTransaction();
    if (transaction == null) {
      throw new EntityNotFoundException("Transaction not found for Order ID: " + orderId);
    }
    transaction.setTransactionStatus(TransactionStatus.SUCCESSFUL);
    transaction.setUpdateAt(LocalDateTime.now());
    Transactions updatedTransaction = transactionsRepository.save(transaction);
    TransactionResponse transactionResponse = transactionMapping.toResponse(updatedTransaction);
    updateOrder(orderId);
    return ApiResponse.<TransactionResponse>builder()
        .status(200)
        .message("Transaction updated successfully")
        .data(transactionResponse)
        .build();
  }

  @Override
  public ApiResponse<TransactionResponse> updateTransactionFailed(Long orderId) {
    Orders orders = ordersRepository.findByOrderId(orderId);
    if (orders == null) {
      throw new EntityNotFoundException("Order not found with ID: " + orderId);
    }
    Transactions transaction = orders.getTransaction();
    if (transaction == null) {
      throw new EntityNotFoundException("Transaction not found for Order ID: " + orderId);
    }
    transaction.setTransactionStatus(TransactionStatus.FAILED);
    transaction.setUpdateAt(LocalDateTime.now());
    Transactions updatedTransaction = transactionsRepository.save(transaction);
    TransactionResponse transactionResponse = transactionMapping.toResponse(updatedTransaction);
    updateOrder(orderId);
    return ApiResponse.<TransactionResponse>builder()
        .status(200)
        .message("Transaction updated successfully")
        .data(transactionResponse)
        .build();
  }

  @Override
  public ApiResponse<List<OrderResponse>> getOrderByUserId(String token) {

    Long userId = jwtUtil.extractUserId(token);
    return ApiResponse.<List<OrderResponse>>builder()
        .status(200)
        .message("Order retrieved successfully")
        .data(orderMapping.toResponseList(ordersRepository.findByUserId(userId)))
        .build();
  }

  @Override
  public ApiResponse<List<OrderResponse.OrderDetailResponse>> getOrderDetailByUserId(String token) {
    Long userId = jwtUtil.extractUserId(token);
    List<Orders> orders = ordersRepository.findByUserId(userId);
    if (orders.isEmpty()) {
      throw new EntityNotFoundException("No orders found for user ID: " + userId);
    }
    List<Long> ticketIds = orders.stream().map(Orders::getTicketId).distinct().toList();
    List<TicketResponse> ticketResponses = ticketClient.getTicketsByIds(ticketIds).getData();
    if (ticketResponses == null || ticketResponses.isEmpty()) {
      throw new EntityNotFoundException("No tickets found for the provided IDs");
    }
    // 🔹 Map ticketId -> TicketResponse để tra nhanh
    Map<Long, TicketResponse> ticketMap =
        ticketResponses.stream().collect(Collectors.toMap(TicketResponse::id, t -> t));
    List<OrderResponse.OrderDetailResponse> orderDetails =
        orders.stream()
            .map(
                order ->
                    OrderResponse.OrderDetailResponse.builder()
                        .orderId(order.getOrderId())
                        .userId(order.getUserId())
                        .status(order.getStatus())
                        .amount(order.getAmount())
                        .ticket(ticketMap.get(order.getTicketId()))
                        .transaction(transactionMapping.toResponse(order.getTransaction()))
                        .build())
            .toList();
    return ApiResponse.<List<OrderResponse.OrderDetailResponse>>builder()
        .status(200)
        .message("Order retrieved successfully")
        .data(orderDetails)
        .build();
  }

  @Override
  public ApiResponse<List<OrderResponse.OrderDetailResponse>> getOrderDetailByStatus(
      String token, TicketStatus status) {
    Long userId = jwtUtil.extractUserId(token);
    List<Orders> orders = ordersRepository.findByUserId(userId);
    if (orders.isEmpty()) {
      throw new EntityNotFoundException("No orders found for user ID: " + userId);
    }
    List<Orders> successfulOrders =
        orders.stream().filter(order -> order.getStatus() == OrderStatus.SUCCESSFUL).toList();

    List<Long> ticketIds = successfulOrders.stream().map(Orders::getTicketId).distinct().toList();
    List<TicketResponse> ticketResponses =
        ticketClient.getTicketsByStatus(ticketIds, status).getData();
    if (ticketResponses == null || ticketResponses.isEmpty()) {
      throw new EntityNotFoundException("No tickets found for the provided IDs");
    }
    // 🔹 Map ticketId -> TicketResponse để tra nhanh
    Map<Long, TicketResponse> ticketMap =
        ticketResponses.stream().collect(Collectors.toMap(TicketResponse::id, t -> t));

    List<OrderResponse.OrderDetailResponse> orderDetails =
        successfulOrders.stream()
            .filter(order -> ticketMap.containsKey(order.getTicketId()))
            .map(
                order ->
                    OrderResponse.OrderDetailResponse.builder()
                        .orderId(order.getOrderId())
                        .userId(order.getUserId())
                        .status(order.getStatus())
                        .amount(order.getAmount())
                        .ticket(ticketMap.get(order.getTicketId()))
                        .build())
            .toList();

    if (orderDetails.isEmpty()) {
      throw new EntityNotFoundException("No orders found with status: " + status);
    }

    return ApiResponse.<List<OrderResponse.OrderDetailResponse>>builder()
        .status(200)
        .message("Order retrieved successfully")
        .data(
            orderDetails.stream()
                .sorted(Comparator.comparing(order -> order.ticket().validUntil()))
                .collect(Collectors.toList()))
        .build();
  }
}
