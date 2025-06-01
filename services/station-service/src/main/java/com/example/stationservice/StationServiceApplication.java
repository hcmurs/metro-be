package com.example.stationservice;

import io.github.lcaohoanq.JavaBrowserLauncher;
import java.util.Arrays;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StationServiceApplication {

    public static void main(String[] args) {
        var context = SpringApplication.run(StationServiceApplication.class, args);

        var env = context.getEnvironment();
        var activeProfiles = env.getActiveProfiles();
        if (!Arrays.asList(activeProfiles).contains("docker")) {
            JavaBrowserLauncher.openHomePage("http://localhost:4004/swagger-ui.html");
        }
    }

}
