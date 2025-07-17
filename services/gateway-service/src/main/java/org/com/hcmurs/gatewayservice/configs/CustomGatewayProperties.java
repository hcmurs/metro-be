package org.com.hcmurs.gatewayservice.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "gateway")
public class CustomGatewayProperties {
    private boolean dockerHost;
}
