package org.com.hcmurs.gatewayservice.config;

import lombok.RequiredArgsConstructor;
import org.com.hcmurs.gatewayservice.security.JwtAuthConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String[] PUBLIC_ENDPOINTS = {
        "/public/**",
        "/actuator/**",
        "/h2-console/**",
        "/graphiql",
        "/graphql",
        "/error",
        "/v3/api-docs/**",
        "/v3/api-docs.yaml",
        "/swagger-ui/**",
        "/swagger-ui.html",
        "/api/v1/swagger-ui.html",
        "/webjars/**",
        "/configuration/**",
        "/swagger-resources/**"
    };

    public static final String HCMURS_MEMBER = "HCMURS_MEMBER";
    public static final String HCMURS_STAFF = "HCMURS_STAFF";
    public static final String HCMURS_ADMIN = "HCMURS_ADMIN";

    private final ServerAuthenticationEntryPoint serverAuthenticationEntryPoint;
    private final ServerAccessDeniedHandler serverAccessDeniedHandler;
    private final JwtAuthConverter jwtAuthConverter;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
            .authorizeExchange(exchanges ->
                                   exchanges
                                       .pathMatchers(PUBLIC_ENDPOINTS).permitAll()

                                       .pathMatchers("/keycloak/**").permitAll()
                                       .pathMatchers("/keycloak/test-member").hasAnyRole(HCMURS_MEMBER)
                                       .pathMatchers("/keycloak/test-admin").hasAnyRole(HCMURS_ADMIN)

                                       .pathMatchers(HttpMethod.POST, "/qr-login").permitAll()
                                       .pathMatchers(HttpMethod.GET, "/qr-login/**").permitAll()

                                       .anyExchange().authenticated()
            )
            .oauth2ResourceServer(oauth2 ->
                                      oauth2.jwt(jwt ->
                                                     jwt.jwtAuthenticationConverter(jwtAuthConverter)
                                      )
            )
            .exceptionHandling(ex -> {
                ex.authenticationEntryPoint(serverAuthenticationEntryPoint);
                ex.accessDeniedHandler(serverAccessDeniedHandler);
            })
            .csrf(csrf -> csrf.disable())
            .build();
    }
}