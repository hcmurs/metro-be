package com.example.cronjob.Pojos;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "station_daily_analytics")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StationDailyAnalytics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "summary_id")
    private Long id;

    @Column(name = "station_id", nullable = false)
    private Integer stationId;

    @Column(name = "report_date", nullable = false)
    private LocalDate reportDate;

    @Column(name = "entries_count", nullable = false)
    private Integer entriesCount = 0;

    @Column(name = "tickets_sold", nullable = false)
    private Integer ticketsSold = 0;

    @Column(name = "revenue_generated", precision = 10, scale = 2)
    private BigDecimal revenueGenerated = BigDecimal.ZERO;

    @Column(name = "peak_hour_usage", nullable = false)
    private Integer peakHourUsage = 0;

    @Column(name = "off_peak_tickets", nullable = false)
    private Integer offPeakTickets = 0;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
