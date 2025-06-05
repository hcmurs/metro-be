package org.alfred.ticketservice.model;

    import jakarta.persistence.*;
    import jakarta.validation.constraints.*;
    import lombok.*;
    import org.alfred.ticketservice.model.enums.UsageTypes;

    import java.time.LocalDateTime;

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