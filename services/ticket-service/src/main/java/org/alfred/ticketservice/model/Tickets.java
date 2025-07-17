package org.alfred.ticketservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
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
import org.alfred.ticketservice.model.enums.TicketStatus;

    @Table(name = "tickets")
    @Setter
    @Getter
    @Entity
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public class Tickets {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "ticket_id")
        private Long ticketId;

        @NotNull(message = "Ticket type is required")
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "ticket_type_id", nullable = false)
        private TicketTypes ticketType;

        @NotBlank(message = "Name is required")
        @Size(min = 3, max = 200, message = "Name must be between 3 and 200 characters")
        @Column(name = "name", nullable = false)
        private String name;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "fare_matrix_id")
        private FareMatrix fareMatrix;

        @NotBlank(message = "Ticket code is required")
        @Size(min = 5, max = 50, message = "Ticket code must be between 5 and 50 characters")
        @Column(name = "ticket_code", unique = true, nullable = false)
        private String ticketCode;

        @NotNull(message = "Actual price is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Actual price must be greater than zero")
        @Column(name = "actual_price", nullable = false)
        private float actualPrice;

        @NotNull(message = "Valid from date is required")
        @Column(name = "valid_from", nullable = false)
        private LocalDateTime validFrom;

        @NotNull(message = "Valid until date is required")
        @Column(name = "valid_until", nullable = false)
        private LocalDateTime validUntil;

        @NotNull(message = "Status is required")
        @Column(name = "status", nullable = false)
        @Enumerated(EnumType.STRING)
        private TicketStatus status;

        @Column(name = "in_trip", nullable = false)
        private boolean inTrip = false;

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