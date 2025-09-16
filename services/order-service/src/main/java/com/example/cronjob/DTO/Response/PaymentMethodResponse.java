/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: Order-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package com.example.cronjob.DTO.Response;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMethodResponse {
  private Integer paymentMethodId;
  private String paymentMethodName;
  private boolean isActive;
}
