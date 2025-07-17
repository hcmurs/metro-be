package org.alfred.ticketservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

    @Table(name = "fare_matrix", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"start_station_id", "end_station_id"}, name = "uk_fare_matrix_stations")
    })
    @Setter
    @Getter
    @Entity
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public class FareMatrix {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "fare_matrix_id")
        private Long fareMatrixId;

        @NotNull(message = "Price is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero")
        @Column(nullable = false)
        private float price;

        @NotBlank(message = "Name is required")
        @Size(max = 100, message = "Name must be less than 100 characters")
        @Column(nullable = false)
        private String name;

        @NotNull(message = "Start station ID is required")
        @Column(name = "start_station_id", nullable = false)
        private Long startStationId;

        @NotNull(message = "End station ID is required")
        @Column(name = "end_station_id", nullable = false)
        private Long endStationId;

        @Column(name = "created_at")
        private LocalDateTime createdAt;

        @Column(name = "updated_at")
        private LocalDateTime updatedAt;

        @Column(name = "is_active", nullable = false)
        private boolean isActive = true;

        @PrePersist
        public void prePersist() {
            createdAt = updatedAt = LocalDateTime.now();
        }

        @PreUpdate
        public void preUpdate() {
            updatedAt = LocalDateTime.now();
        }
    }