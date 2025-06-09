package com.hieunn.user_service.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RequestCreationRequest {
    String content;
    @NotBlank
    String studentCardImage;
    @NotBlank
    String citizenIdentityCardImage;
}
