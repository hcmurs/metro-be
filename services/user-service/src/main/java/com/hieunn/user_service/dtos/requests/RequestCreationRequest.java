package com.hieunn.user_service.dtos.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RequestCreationRequest {
    String content;
    @Pattern(regexp = "\\d{12}", message = "Citizen ID must be exactly 12 digits")
    String citizenIdNumber;
    @NotBlank
    String studentCardImage;
    @NotBlank
    String citizenIdentityCardImage;
    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate endDate;
}
