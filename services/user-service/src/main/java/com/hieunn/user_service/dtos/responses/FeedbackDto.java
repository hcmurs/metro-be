package com.hieunn.user_service.dtos.responses;

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
public class FeedbackDto {
    Long feedbackId;
    String category;
    String content;
    String image;
    String reply;
    Long userId;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
