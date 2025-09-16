/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: Order-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package com.example.cronjob.Pojos;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "payment_methods")
@Builder
public class PaymentMethod {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "payment_method_id")
  private Long paymentMethodId;

  @Column(nullable = false, name = "method_name")
  private String name;

  @Column(nullable = false, name = "is_active")
  private Boolean isActive = true;

  public PaymentMethod() {}

  public PaymentMethod(Long paymentMethodId, String name, Boolean isActive) {
    this.paymentMethodId = paymentMethodId;
    this.name = name;
    this.isActive = isActive;
  }

  public Long getPaymentMethodId() {
    return paymentMethodId;
  }

  public void setPaymentMethodId(Long paymentMethodId) {
    this.paymentMethodId = paymentMethodId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Boolean getActive() {
    return isActive;
  }

  public void setActive(Boolean active) {
    isActive = active;
  }
}
