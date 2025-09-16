/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: User-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package com.hieunn.user_service.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "feedbacks")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Feedback {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "feedback_id")
  Long feedbackId;

  @Column(name = "category", nullable = false)
  @NotBlank
  String category;

  @Column(name = "content", nullable = false)
  @NotBlank
  String content;

  @Column(name = "image", columnDefinition = "TEXT")
  String image;

  @Column(name = "reply")
  String reply;

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
