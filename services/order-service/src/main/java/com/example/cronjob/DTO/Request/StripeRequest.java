/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: Order-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package com.example.cronjob.DTO.Request;

import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@Data
public class StripeRequest {
  @Builder
  public record ProductRequest(@Positive Long orderId) {}
}
