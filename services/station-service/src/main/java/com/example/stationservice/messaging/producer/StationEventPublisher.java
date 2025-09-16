/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: Station-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package com.example.stationservice.messaging.producer;

import com.example.stationservice.config.RabbitMQConfig;
import com.example.stationservice.dto.StationEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StationEventPublisher {

  private final RabbitTemplate rabbitTemplate;

  public void publishStationDeleted(StationEvent event) {
    rabbitTemplate.convertAndSend(
        RabbitMQConfig.STATION_EXCHANGE, RabbitMQConfig.STATION_DELETE_ROUTING_KEY, event);
  }

  public void publishStationAdded(StationEvent event) {
    rabbitTemplate.convertAndSend(
        RabbitMQConfig.STATION_EXCHANGE, RabbitMQConfig.STATION_ADD_ROUTING_KEY, event);
  }
}
