package org.alfred.ticketservice.model;

    import jakarta.persistence.*;
    import jakarta.validation.constraints.*;
    import lombok.*;

    import java.math.BigDecimal;
    import java.time.LocalDateTime;

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

        @NotNull(message = "distanceInKm is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "distanceInKm must be greater than zero")
        @Column(nullable = false)
        private int distanceInKm;

        @ManyToOne()
        @JoinColumn(name = "fare_pricing_id", nullable = false)
        private FarePricing farePricing;

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
        // Trong FareMatrix
        @AssertTrue(message = "Start and end stations must be different")
        public boolean isValidStations() {
            return !startStationId.equals(endStationId);
        }

        @PrePersist
        public void prePersist() {
            createdAt = updatedAt = LocalDateTime.now();
        }

        @PreUpdate
        public void preUpdate() {
            updatedAt = LocalDateTime.now();
        }
    }