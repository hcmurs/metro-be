/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: Notification-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package com.hieunn.notificationservice.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtAuthenticationFilter jwtAuthFilter;

  private final JwtAuthenticationEntryPoint authenticationEntryPoint;

  private final CustomAccessDeniedHandler accessDeniedHandler;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http.csrf(AbstractHttpConfigurer::disable)
        //                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(
            auth ->
                auth.requestMatchers(
                        "/api/ts/fare-matrices/{id}",
                        "/api/ts/fare-matrices",
                        "/api/ts/fare-matrices/by-station/{stationId}",
                        "/api/ts/fare-matrices/get-fare")
                    .permitAll()
                    .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "swagger-ui.html/**")
                    .permitAll()
                    .anyRequest()
                    .authenticated())
        // Handle exceptions for unauthorized access and access denied filters
        // if use the rule url
        .exceptionHandling(
            exception ->
                exception
                    .authenticationEntryPoint(authenticationEntryPoint)
                    .accessDeniedHandler(accessDeniedHandler))
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
  }

  //    @Bean
  //    public CorsConfigurationSource corsConfigurationSource() {
  //        CorsConfiguration configuration = new CorsConfiguration();
  //        configuration.addAllowedOrigin("*");
  //        configuration.addAllowedMethod("*");
  //        configuration.addAllowedHeader("*");
  //        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
  //        source.registerCorsConfiguration("/**", configuration);
  //        return source;
  //    }
}
