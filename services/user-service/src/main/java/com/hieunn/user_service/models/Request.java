package com.hieunn.user_service.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

    @Column(name = "student_card_image", nullable = false)
    @NotBlank
    String studentCardImage;

    @Column(name = "citizen_identity_card_image", nullable = false)
    @NotBlank
    String citizenIdentityCardImage;

    @Enumerated(EnumType.STRING)
    @Column(name = "requestStatus", nullable = false)
    @Builder.Default
    RequestStatus requestStatus = RequestStatus.PENDING;

    @Column(name = "start_date")
    LocalDateTime startDate;

    @Column(name = "end_date")
    LocalDateTime endDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    LocalDateTime updatedAt;
}
