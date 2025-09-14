/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package com.hieunn.cronjobservice.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ticket_type_statistics")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketTypeStatistic {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "ticket_type", nullable = false)
  private String ticketType;

  @Column(name = "usage_date", nullable = false)
  private LocalDate usageDate;

  @Column(name = "usage_count")
  private Integer usageCount = 0;

  @Column(
      name = "created_at",
      updatable = false,
      insertable = false,
      columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private LocalDateTime createdAt;
}
