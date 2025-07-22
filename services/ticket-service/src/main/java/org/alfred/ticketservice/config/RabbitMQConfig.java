package org.alfred.ticketservice.config;

import org.alfred.ticketservice.dto.StationEvent;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {

    public static final String STATION_EXCHANGE = "station.exchange";
    public static final String STATION_DELETE_ROUTING_KEY = "station.delete";
    public static final String STATION_ADD_ROUTING_KEY = "station.add";

    // Ticket service queues để listen station events
    public static final String TICKET_STATION_DELETE_QUEUE = "ticket.station.delete.queue";
    public static final String TICKET_STATION_ADD_QUEUE = "ticket.station.add.queue";

    // Dead Letter Queue configuration
    public static final String DLX_EXCHANGE = "dlx.exchange";
    public static final String TICKET_STATION_DELETE_DLQ = "ticket.station.delete.dlq";
    public static final String TICKET_STATION_ADD_DLQ = "ticket.station.add.dlq";

    @Bean
    public TopicExchange stationExchange() {
        return new TopicExchange(STATION_EXCHANGE);
    }

    @Bean
    public TopicExchange deadLetterExchange() {
        return new TopicExchange(DLX_EXCHANGE);
    }

    // Queue để listen station delete events
    @Bean
    public Queue ticketStationDeleteQueue() {
        return QueueBuilder.durable(TICKET_STATION_DELETE_QUEUE)
                .withArgument("x-dead-letter-exchange", DLX_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", TICKET_STATION_DELETE_DLQ)
                .build();
    }

    // Queue để listen station add events
    @Bean
    public Queue ticketStationAddQueue() {
        return QueueBuilder.durable(TICKET_STATION_ADD_QUEUE)
                .withArgument("x-dead-letter-exchange", DLX_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", TICKET_STATION_ADD_DLQ)
                .build();
    }

    // Dead Letter Queues
    @Bean
    public Queue ticketStationDeleteDLQ() {
        return QueueBuilder.durable(TICKET_STATION_DELETE_DLQ).build();
    }

    @Bean
    public Queue ticketStationAddDLQ() {
        return QueueBuilder.durable(TICKET_STATION_ADD_DLQ).build();
    }

    // Bindings cho main queues
    @Bean
    public Binding ticketStationDeleteBinding() {
        return BindingBuilder
                .bind(ticketStationDeleteQueue())
                .to(stationExchange())
                .with(STATION_DELETE_ROUTING_KEY);
    }

    @Bean
    public Binding ticketStationAddBinding() {
        return BindingBuilder
                .bind(ticketStationAddQueue())
                .to(stationExchange())
                .with(STATION_ADD_ROUTING_KEY);
    }

    // Bindings cho DLQ
    @Bean
    public Binding ticketStationDeleteDLQBinding() {
        return BindingBuilder
                .bind(ticketStationDeleteDLQ())
                .to(deadLetterExchange())
                .with(TICKET_STATION_DELETE_DLQ);
    }

    @Bean
    public Binding ticketStationAddDLQBinding() {
        return BindingBuilder
                .bind(ticketStationAddDLQ())
                .to(deadLetterExchange())
                .with(TICKET_STATION_ADD_DLQ);
    }


}
