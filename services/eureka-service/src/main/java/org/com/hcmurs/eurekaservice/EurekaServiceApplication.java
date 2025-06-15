package org.com.hcmurs.eurekaservice;

import io.github.lcaohoanq.JavaBrowserLauncher;
import java.util.Arrays;
import java.util.List;
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
		List<String> profilesList = Arrays.asList(activeProfiles);

		if (!profilesList.contains("docker") && !profilesList.contains("test") && !profilesList.contains("zimaos")) {
			JavaBrowserLauncher.openHomePage("http://localhost:8761");
		}
	}
}