package org.alfred.ticketservice.model;

    import jakarta.persistence.*;
    import jakarta.validation.constraints.*;
    import lombok.*;
    import org.alfred.ticketservice.model.enums.Duration;

    import java.math.BigDecimal;
    import java.time.LocalDateTime;

    @Table(name = "ticket_types")
    @Setter
    @Getter
    @Entity
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public class TicketTypes {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "ticket_type_id")
        private Long ticketTypeId;

        @NotBlank(message = "Name is required")
        @Size(max = 100, message = "Name must be less than 100 characters")
        @Column(nullable = false)
        private String name;

        @NotNull(message = "Price is required")
        @DecimalMin(value = "0.0", message = "Price must be greater than zero")
        @Column(nullable = false)
        private float price;

        @Size(max = 500, message = "Description must be less than 500 characters")
        private String description;

        @NotNull(message = "Validity duration is required")
        @Column(name = "validity_duration", nullable = false)
        private int validityDuration; // in minutes

        private boolean forStudent;

        @Column(name = "is_active", nullable = false)
        private boolean isActive = true;

        @Column(name = "created_at")
        private LocalDateTime createdAt;

        @Column(name = "updated_at")
        private LocalDateTime updatedAt;

        @PrePersist
        public void prePersist() {
            createdAt = updatedAt = LocalDateTime.now();
        }

        @PreUpdate
        public void preUpdate() {
            updatedAt = LocalDateTime.now();
        }
    }