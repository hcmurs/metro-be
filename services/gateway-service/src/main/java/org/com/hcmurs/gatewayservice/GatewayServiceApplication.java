package org.com.hcmurs.gatewayservice;

import io.github.lcaohoanq.JavaBrowserLauncher;
import java.util.Arrays;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GatewayServiceApplication {

    public static void main(String[] args) {
        var context = SpringApplication.run(GatewayServiceApplication.class, args);

        var env = context.getEnvironment();
        var activeProfiles = env.getActiveProfiles();
        if (!Arrays.asList(activeProfiles).contains("docker")) {
            JavaBrowserLauncher.openHomePage("http://localhost:4003/swagger-ui.html");
        }
    }

}
