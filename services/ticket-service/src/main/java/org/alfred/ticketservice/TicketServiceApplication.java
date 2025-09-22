/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 * <p>
 * Service: Ticket-Service
 * <p>
 * This software is the confidential and proprietary information of hcmurs. You shall not disclose such confidential
 * information and shall use it only in accordance with the terms of the license agreement you entered into with
 * hcmurs.
 */
package org.alfred.ticketservice;

import io.github.lcaohoanq.annotations.BrowserLauncher;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableFeignClients
@SpringBootApplication
@EnableDiscoveryClient
@EnableScheduling
@EnableRabbit
@BrowserLauncher(value = "http://localhost:4005/swagger-ui.html")
public class TicketServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TicketServiceApplication.class, args);
    }
}
