package com.example.stationservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String STATION_EXCHANGE = "station.exchange";

    public static final String STATION_DELETE_QUEUE = "station.delete.queue";
    public static final String STATION_DELETE_ROUTING_KEY = "station.delete";
    public static final String STATION_ADD_QUEUE = "station.add.queue";
    public static final String STATION_ADD_ROUTING_KEY = "station.add";
    public static final String STATION_ADD_DLQ = "station.add.dlq";

    public static final String DLX_EXCHANGE = "dlx.exchange";
    public static final String DLQ_QUEUE = "station.delete.dlq";

    @Bean
    public TopicExchange stationExchange() {
        return new TopicExchange(STATION_EXCHANGE);
    }

    @Bean
    public TopicExchange deadLetterExchange() {
        return new TopicExchange(DLX_EXCHANGE);
    }

    @Bean
    public Queue stationDeleteQueue() {
        return QueueBuilder.durable(STATION_DELETE_QUEUE)
                .withArgument("x-dead-letter-exchange", DLX_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", "station.delete.dlq")
                .build();
    }

    @Bean
    public Queue stationAddQueue() {
        return QueueBuilder.durable(STATION_ADD_QUEUE)
                .withArgument("x-dead-letter-exchange", DLX_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", "station.add.dlq")
                .build();
    }
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        return template;
    }

    @Bean
    public Queue stationDeleteDLQ() {
        return QueueBuilder.durable(DLQ_QUEUE).build();
    }

    @Bean
    public Queue stationAddDLQ() {
        return QueueBuilder.durable(STATION_ADD_DLQ).build();
    }

    @Bean
    public Binding stationDeleteBinding() {
        return BindingBuilder
                .bind(stationDeleteQueue())
                .to(stationExchange())
                .with(STATION_DELETE_ROUTING_KEY);
    }

    @Bean
    public Binding stationAddBinding() {
        return BindingBuilder
                .bind(stationAddQueue())
                .to(stationExchange())
                .with(STATION_ADD_ROUTING_KEY);
    }

    @Bean
    public Binding dlqDeleteBinding() {
        return BindingBuilder
                .bind(stationDeleteDLQ())
                .to(deadLetterExchange())
                .with("station.delete.dlq");
    }

    @Bean
    public Binding dlqAddBinding() {
        return BindingBuilder
                .bind(stationAddDLQ())
                .to(deadLetterExchange())
                .with("station.add.dlq");
    }
}
