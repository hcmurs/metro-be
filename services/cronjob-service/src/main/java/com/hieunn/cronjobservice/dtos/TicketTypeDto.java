package com.hieunn.cronjobservice.dtos;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TicketTypeDto {
    Long id;
    String name;
    String description;
    float price;
    int validityDuration;
    boolean isActive;
    boolean forStudent;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
