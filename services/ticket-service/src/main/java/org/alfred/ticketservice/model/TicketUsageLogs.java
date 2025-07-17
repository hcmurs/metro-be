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
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.alfred.ticketservice.model.enums.UsageTypes;

    @Table(name = "ticket_usage_logs")
    @Setter
    @Getter
    @Entity
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public class TicketUsageLogs {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "ticket_usage_log_id")
        private Long ticketUsageLogId;

        @NotNull(message = "Ticket is required")
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "ticket_id", nullable = false)
        private Tickets ticket;

        @NotNull(message = "Station ID is required")
        @Column(name = "station_id", nullable = false)
        private Long stationId;

        @NotNull(message = "Usage time is required")
        @Column(name = "usage_time", nullable = false)
        private LocalDateTime usageTime;

        @NotNull(message = "Usage type is required")
        @Column(name = "usage_type", nullable = false)
        @Enumerated(EnumType.STRING)
        private UsageTypes usageType;
    }