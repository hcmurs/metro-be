package com.hieunn.notificationservice.services;

import com.hieunn.notificationservice.dtos.OrderCompletedEvent;

public interface OrderEventConsumer {
    void handleOrderCompleted(OrderCompletedEvent event) throws Exception;
}
