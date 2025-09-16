/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: User-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package com.hieunn.user_service.dtos.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hieunn.user_service.models.Request.RequestStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RequestDto {
  Long requestId;
  String title;
  String content;
  String rejectionReason;
  String citizenIdNumber;
  String studentCardImage;
  String citizenIdentityCardImage;
  RequestStatus requestStatus;

  @JsonFormat(pattern = "dd/MM/yyyy")
  LocalDate startDate;

  @JsonFormat(pattern = "dd/MM/yyyy")
  LocalDate endDate;

  // Foreign key to User
  Long userId;

  @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
  LocalDateTime createdAt;

  @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
  LocalDateTime updatedAt;
}
