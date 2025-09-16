/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: Ticket-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package org.alfred.ticketservice.exception;

public class QrProcessingException extends RuntimeException {
  public QrProcessingException(String message) {
    super(message);
  }

  public QrProcessingException(String message, Throwable cause) {
    super(message, cause);
  }
}
