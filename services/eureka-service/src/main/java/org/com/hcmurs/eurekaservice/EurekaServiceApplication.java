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
//		System.setProperty("server.port", "8761");
		var context = SpringApplication.run(EurekaServiceApplication.class, args);

		var env = context.getEnvironment();
		var activeProfiles = env.getActiveProfiles();
		List<String> profilesList = Arrays.asList(activeProfiles);

		if (!profilesList.contains("docker") && !profilesList.contains("test") && !profilesList.contains("zimaos")) {
			// Get the actual port from environment
			String port = env.getProperty("server.port", "8761");
			String url = "http://localhost:" + port;

			// Health check before opening browser
			try {
				java.net.http.HttpClient client = java.net.http.HttpClient.newBuilder()
					.connectTimeout(java.time.Duration.ofSeconds(5))
					.build();
				java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
					.uri(java.net.URI.create(url + "/actuator/health"))
					.timeout(java.time.Duration.ofSeconds(5))
					.GET()
					.build();

				java.net.http.HttpResponse<String> response = client.send(
					request, java.net.http.HttpResponse.BodyHandlers.ofString());

				if (response.statusCode() == 200) {
					System.out.println("Eureka server is healthy, opening browser at " + url);
					JavaBrowserLauncher.openHomePage(url);
				} else {
					System.out.println("Eureka server health check failed with status: " + response.statusCode());
				}
			} catch (Exception e) {
				System.out.println("Eureka server health check failed: " + e.getMessage());
			}
		}
	}
}