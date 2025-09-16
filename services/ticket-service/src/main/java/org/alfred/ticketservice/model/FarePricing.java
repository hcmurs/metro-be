/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: Ticket-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package org.alfred.ticketservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import lombok.*;

@Table(name = "fare_pricing")
@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FarePricing {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "min_distance_km", nullable = false, unique = true)
  private int minDistanceKm;

  @Column(name = "max_distance_km", nullable = false, unique = true)
  private int maxDistanceKm;

  @Column(name = "price", nullable = false)
  private float price;

  @Column(name = "is_active", nullable = false)
  private boolean isActive = true;

  @AssertTrue(message = "Max distance must be greater than min distance")
  public boolean isValidDistanceRange() {
    return maxDistanceKm > minDistanceKm;
  }
}
