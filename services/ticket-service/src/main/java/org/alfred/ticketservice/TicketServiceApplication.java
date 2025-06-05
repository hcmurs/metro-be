package org.alfred.ticketservice;

import io.github.lcaohoanq.JavaBrowserLauncher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.Arrays;

@EnableFeignClients
@SpringBootApplication
@EnableDiscoveryClient
public class TicketServiceApplication {

    public static void main(String[] args) {
        var context = SpringApplication.run(TicketServiceApplication.class, args);

        var env = context.getEnvironment();
        var activeProfiles = env.getActiveProfiles();
        if (!Arrays.asList(activeProfiles).contains("docker")) {
            JavaBrowserLauncher.openHomePage("http://localhost:4005/swagger-ui.html");
        }
    }

}
