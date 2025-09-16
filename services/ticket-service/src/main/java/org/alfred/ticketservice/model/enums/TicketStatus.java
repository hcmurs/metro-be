/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: Ticket-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package org.alfred.ticketservice.model.enums;

public enum TicketStatus {
  USED("used"),
  EXPIRED("expired"),
  NOT_USED("not_used"),
  PENDING("pending"),
  CANCELLED("cancelled");

  private final String status;

  TicketStatus(String status) {
    this.status = status;
  }

  public String getStatus() {
    return status;
  }
}
