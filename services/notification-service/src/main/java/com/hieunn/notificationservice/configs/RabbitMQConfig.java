package com.hieunn.notificationservice.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

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
