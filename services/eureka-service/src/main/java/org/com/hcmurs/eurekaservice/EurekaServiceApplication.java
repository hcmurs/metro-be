package org.com.hcmurs.eurekaservice;

import io.github.lcaohoanq.JavaBrowserLauncher;
import java.util.Arrays;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServiceApplication {

	public static void main(String[] args) {
		var context = SpringApplication.run(EurekaServiceApplication.class, args);

		var env = context.getEnvironment();
		var activeProfiles = env.getActiveProfiles();
		if (!Arrays.asList(activeProfiles).contains("docker")) {
			JavaBrowserLauncher.openHomePage("http://localhost:8761");
		}

	}

}
