/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: Gateway-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package org.com.hcmurs.gatewayservice;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.com.hcmurs.gatewayservice.configs.CRLFLogConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.core.env.Environment;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayServiceApplication {

  private static final Logger LOG = LoggerFactory.getLogger(GatewayServiceApplication.class);

  private final Environment env;

  public GatewayServiceApplication(Environment env) {
    this.env = env;
  }

  public static void main(String[] args) {
    var env = SpringApplication.run(GatewayServiceApplication.class, args);
    logApplicationStartup(env.getEnvironment());
  }

  private static void logApplicationStartup(Environment env) {
    String protocol =
        Optional.ofNullable(env.getProperty("server.ssl.key-store"))
            .map(key -> "https")
            .orElse("http");
    String applicationName = env.getProperty("spring.application.name");
    String serverPort = env.getProperty("server.port");
    String contextPath =
        Optional.ofNullable(env.getProperty("server.servlet.context-path"))
            .filter(StringUtils::isNotBlank)
            .orElse("/");
    String swaggerUIPath =
        Optional.ofNullable(env.getProperty("springdoc.swagger-ui.path"))
            .orElse("/swagger-ui.html");
    String hostAddress = "localhost";
    try {
      hostAddress = InetAddress.getLocalHost().getHostAddress();
    } catch (UnknownHostException e) {
      LOG.warn("The host name could not be determined, using `localhost` as fallback");
    }
    LOG.info(
        CRLFLogConverter.CRLF_SAFE_MARKER,
        """

            ----------------------------------------------------------
            \tApplication '{}' is running! Access URLs:
            \tLocal: \t\t{}://localhost:{}{}
            \tExternal: \t{}://{}:{}{}
            \tProfile(s): \t{}
            ----------------------------------------------------------""",
        applicationName,
        protocol,
        serverPort,
        contextPath,
        protocol,
        hostAddress,
        serverPort,
        contextPath,
        env.getActiveProfiles().length == 0 ? env.getDefaultProfiles() : env.getActiveProfiles());
  }
}
