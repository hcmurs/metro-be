package com.example.cronjob;

import io.github.lcaohoanq.JavaBrowserLauncher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Arrays;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class OrderApplication {

    public static void main(String[] args) {
        var context = SpringApplication.run(OrderApplication.class, args);

        var env = context.getEnvironment();
        var activeProfiles = env.getActiveProfiles();
        if (!Arrays.asList(activeProfiles).contains("docker") && !Arrays.asList(activeProfiles).contains("test") && !Arrays.asList(activeProfiles).contains("zimaos")) {
            JavaBrowserLauncher.openHomePage("http://localhost:4009/swagger-ui.html");
        }
    }

}
