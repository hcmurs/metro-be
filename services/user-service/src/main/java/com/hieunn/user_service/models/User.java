package com.hieunn.user_service.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "google_id")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    Long userId;

    @NotEmpty
    @Email
    @Column(name = "email", unique = true)
    String email;

    @Column(name = "name")
    String name;

    @Column(name = "google_id", unique = true)
    String googleId;

    @Enumerated(EnumType.STRING)
    @Column(name = "auth_provider", nullable = false)
    AuthProvider authProvider;

    @Column(name = "picture_url")
    String pictureUrl;

    @Column(name = "role", nullable = false)
    String role;

    @Column(name = "is_student", nullable = false)
    boolean isStudent = false;

    @Column(name = "student_expired_date")
    LocalDateTime studentExpiredDate;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    LocalDateTime updatedAt;
}