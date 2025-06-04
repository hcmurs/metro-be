package com.hieunn.user_service.dtos.responses;

import com.hieunn.user_service.models.Request.RequestStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RequestDto {
    Long requestId;
    String content;
    String studentCardImage;
    String citizenIdentityCardImage;
    RequestStatus requestStatus;
    LocalDateTime startDate;
    LocalDateTime endDate;
    //Foreign key to User
    Long userId;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
