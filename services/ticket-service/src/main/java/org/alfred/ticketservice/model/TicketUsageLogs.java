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
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import lombok.*;
import org.alfred.ticketservice.model.enums.UsageTypes;

@Table(name = "ticket_usage_logs")
@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketUsageLogs {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ticket_usage_log_id")
  private Long ticketUsageLogId;

  @NotNull(message = "Ticket is required")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ticket_id", nullable = false)
  private Tickets ticket;

  @NotNull(message = "Station ID is required")
  @Column(name = "station_id", nullable = false)
  private Long stationId;

  @NotNull(message = "Usage time is required")
  @Column(name = "usage_time", nullable = false)
  private LocalDateTime usageTime;

  @NotNull(message = "Usage type is required")
  @Column(name = "usage_type", nullable = false)
  @Enumerated(EnumType.STRING)
  private UsageTypes usageType;
}
