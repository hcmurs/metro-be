package com.hieunn.user_service.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Request {
    public enum RequestStatus {
        PENDING,
        APPROVED,
        REJECTED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    Long requestId;

    @Column(name = "content")
    @NotBlank
    String content;

    @Column(name = "title")
    @NotBlank
    String title;

    @Column(name = "rejection_reason")
    String rejectionReason;

    @Pattern(regexp = "\\d{12}", message = "Citizen ID must be exactly 12 digits")
    @Column(name = "citizen_id_number", nullable = false)
    String citizenIdNumber;

    @Column(name = "student_card_image", nullable = false, columnDefinition = "TEXT")
    @NotBlank
    String studentCardImage;

    @Column(name = "citizen_identity_card_image", nullable = false, columnDefinition = "TEXT")
    @NotBlank
    String citizenIdentityCardImage;

    @Enumerated(EnumType.STRING)
    @Column(name = "requestStatus", nullable = false)
    @Builder.Default
    RequestStatus requestStatus = RequestStatus.PENDING;

    @Column(name = "start_date")
    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate startDate;

    @Column(name = "end_date")
    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    LocalDateTime updatedAt;
}
