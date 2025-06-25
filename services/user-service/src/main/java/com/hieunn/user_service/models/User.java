package com.hieunn.user_service.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "google_id"),
        @UniqueConstraint(columnNames = "facebook_id"),
        @UniqueConstraint(columnNames = "username")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    Long userId;

    @Column(name = "username", unique = true)
    String username;

    @Column(name = "password")
    String password;

    @NotEmpty
    @Email
    @Column(name = "email", unique = true, nullable = false)
    String email;

    @Column(name = "name")
    String name;

    @Column(name = "google_id", unique = true)
    String googleId;

    @Column(name = "facebook_id", unique = true)
    String facebookId;

    @Enumerated(EnumType.STRING)
    @Column(name = "auth_provider", nullable = false)
    AuthProvider authProvider;

    @Column(name = "picture_url")
    String pictureUrl;

    @Column(name = "role", nullable = false)
    @Builder.Default
    String role = "ROLE_CUSTOMER";

    @Column(name = "is_student", nullable = false)
    @Builder.Default
    boolean isStudent = false;

    @Column(name = "student_expired_date")
    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate studentExpiredDate;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    LocalDateTime updatedAt;

    public void setRole(String role) {
        this.role = role.toUpperCase();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String formattedRole = this.role.startsWith("ROLE_") ? this.role : "ROLE_" + this.role;
        return List.of(new SimpleGrantedAuthority(formattedRole));
    }
}