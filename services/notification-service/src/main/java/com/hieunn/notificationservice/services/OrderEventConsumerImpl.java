/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: Notification-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package com.hieunn.notificationservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper; // Dùng để deserialize JSON
import com.hieunn.notificationservice.domain.notifications.NotificationEntity;
import com.hieunn.notificationservice.domain.notifications.NotificationService;
import com.hieunn.notificationservice.dtos.OrderCompletedEvent;
import com.rabbitmq.client.Channel; // Channel của RabbitMQ để ack/nack
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message; // Message object từ Spring AMQP
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderEventConsumerImpl {
  private final NotificationService notificationService;
  private final ObjectMapper objectMapper;

  @RabbitListener(queues = "order.completed.queue", ackMode = "MANUAL")
  public void handleOrderCompleted(Message message, Channel channel) {
    long deliveryTag = message.getMessageProperties().getDeliveryTag();

    try {
      // ✅ Deserialize JSON thủ công
      OrderCompletedEvent event =
          objectMapper.readValue(message.getBody(), OrderCompletedEvent.class);

      if (event == null) {
        log.warn("⚠️ Received null event, rejecting...");
        channel.basicNack(deliveryTag, false, false); // không requeue
        return;
      }

      log.info("📩 Nhận event order.completed: {}", event);

      // ✅ Gửi thông báo tùy theo loại event
      NotificationEntity.NotificationType type =
          "Buy ticket success".equalsIgnoreCase(event.getTittle())
              ? NotificationEntity.NotificationType.SUCCESS
              : NotificationEntity.NotificationType.ERROR;

      notificationService.sendNotificationToUser(
          event.getUserEmail(), event.getTittle(), event.getBody(), type);

      // ✅ Xác nhận message đã xử lý thành công
      channel.basicAck(deliveryTag, false);
      log.info("✅ Message [{}] acked successfully", deliveryTag);

    } catch (JsonProcessingException e) {
      // ❌ Lỗi JSON → message lỗi, không parse được → reject luôn
      log.error("❌ Lỗi khi parse JSON: {}", e.getMessage());
      nackAndSkip(channel, deliveryTag);
    } catch (Exception e) {
      // ❌ Lỗi xử lý logic → có thể tạm thời (service down, DB error, v.v.)
      log.error("❌ Lỗi khi xử lý event: {}", e.getMessage(), e);
      retryLater(channel, deliveryTag);
    }
  }

  private void nackAndSkip(Channel channel, long tag) {
    try {
      channel.basicNack(tag, false, false); // không requeue
    } catch (Exception ex) {
      log.error("⚠️ Failed to nack message: {}", ex.getMessage());
    }
  }

  private void retryLater(Channel channel, long tag) {
    try {
      channel.basicNack(tag, false, true); // requeue = true (đưa lại vào hàng đợi)
    } catch (Exception ex) {
      log.error("⚠️ Failed to requeue message: {}", ex.getMessage());
    }
  }
}
