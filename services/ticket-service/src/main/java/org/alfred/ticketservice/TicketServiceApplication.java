package org.alfred.ticketservice;

import io.github.lcaohoanq.JavaBrowserLauncher;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Arrays;

@EnableFeignClients
@SpringBootApplication
@EnableDiscoveryClient
@EnableScheduling
@EnableRabbit
public class TicketServiceApplication {
    public static void main(String[] args) {
        var context = SpringApplication.run(TicketServiceApplication.class, args);

        var env = context.getEnvironment();
        var activeProfiles = env.getActiveProfiles();
        if (!Arrays.asList(activeProfiles).contains("docker") && !Arrays.asList(activeProfiles).contains("test") && !Arrays.asList(activeProfiles).contains("zimaos")) {
            JavaBrowserLauncher.openHomePage("http://localhost:4005/swagger-ui.html");
        }
    }

}
