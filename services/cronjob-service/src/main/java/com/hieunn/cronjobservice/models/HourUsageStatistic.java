package com.hieunn.cronjobservice.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "peak_hour_statistics")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HourUsageStatistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usage_date", nullable = false)
    private LocalDate usageDate;

    @Column(name = "start_hour", nullable = false)
    private Integer startHour;

    @Column(name = "end_hour", nullable = false)
    private Integer endHour;

    @Column(name = "entry_count")
    private Integer entryCount;

    @Column(name = "exit_count")
    private Integer exitCount;

    @Column(name = "created_at", updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;
}
