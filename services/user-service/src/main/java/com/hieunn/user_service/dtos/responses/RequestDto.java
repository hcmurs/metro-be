package com.hieunn.user_service.dtos.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hieunn.user_service.models.Request.RequestStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    //Foreign key to User
    Long userId;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    LocalDateTime createdAt;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    LocalDateTime updatedAt;
}
