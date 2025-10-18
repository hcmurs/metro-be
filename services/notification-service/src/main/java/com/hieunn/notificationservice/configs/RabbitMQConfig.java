/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: Notification-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package com.hieunn.notificationservice.configs;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
  public static final String ORDER_EXCHANGE = "order.exchange";
  public static final String ORDER_COMPLETED_QUEUE = "order.completed.queue";
  public static final String ORDER_COMPLETED_ROUTING_KEY = "order.completed";

  @Bean
  public TopicExchange orderExchange() {
    return new TopicExchange(ORDER_EXCHANGE);
  }

  @Bean
  public Queue orderCompletedQueue() {
    return QueueBuilder.durable(ORDER_COMPLETED_QUEUE).build();
  }

  @Bean
  public Binding orderCompletedBinding() {
    return BindingBuilder.bind(orderCompletedQueue())
        .to(orderExchange())
        .with(ORDER_COMPLETED_ROUTING_KEY);
  }

  //     JSON converter (rất quan trọng!)
  //    @Bean
  //    public MessageConverter jsonMessageConverter(ObjectMapper objectMapper) {
  //        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter(objectMapper);
  //
  //        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();
  //
  //        // ✅ Tin tưởng tất cả package, tránh lỗi "Not trusted package"
  //        typeMapper.setTrustedPackages("*");
  //
  //        // ✅ Ưu tiên infer type từ content thay vì header
  //        typeMapper.setTypePrecedence(DefaultJackson2JavaTypeMapper.TypePrecedence.INFERRED);
  //
  //        // ✅ Không dùng type header để deserialize (bỏ __TypeId__)
  //        // => Khi đó converter sẽ trả về LinkedHashMap nếu chưa biết type
  //        typeMapper.setIdClassMapping(Collections.emptyMap());
  //        converter.setJavaTypeMapper(typeMapper);
  //
  //        // ✅ Không cần tạo message ID
  //        converter.setCreateMessageIds(false);
  //
  //        return converter;
  //    }
  //
  //    @Bean
  //    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
  //        RabbitTemplate template = new RabbitTemplate(connectionFactory);
  //        template.setMessageConverter(jsonMessageConverter(new ObjectMapper()));
  //        return template;
  //    }
}
