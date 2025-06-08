package com.example.cronjob.Pojos;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDate;

@Entity
@Table(name = "daily_analytics")
@Data
@Builder
public class DailyAnalytics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "analytics_id")
    private Long id;

    @Column(name = "report_date", nullable = false)
    private LocalDate reportDate;

    @Column(name = "total_tickets_sold", nullable = false)
    private Integer totalTicketsSold = 0;

    @Column(name = "total_revenue", precision = 12, scale = 2)
    private BigDecimal totalRevenue;

    @Column(name = "total_passengers", nullable = false)
    private Integer totalPassengers = 0;

    @Column(name = "peak_hour_tickets", nullable = false)
    private Integer peakHourTickets = 0;

    @Column(name = "off_peak_tickets", nullable = false)
    private Integer offPeakTickets = 0;

    @Column(name = "single_journey_count", nullable = false)
    private Integer singleJourneyCount = 0;

    @Column(name = "pass_tickets_count", nullable = false)
    private Integer passTicketsCount = 0;

    @Column(name = "one_day_tickets_count", nullable = false)
    private Integer oneDayTicketsCount = 0;

    @Column(name = "three_day_tickets_count", nullable = false)
    private Integer threeDayTicketsCount = 0;

    @Column(name = "week_tickets_count", nullable = false)
    private Integer weekTicketsCount = 0;

    @Column(name = "month_tickets_count", nullable = false)
    private Integer monthTicketsCount = 0;

    @Column(name = "student_tickets_count", nullable = false)
    private Integer studentTicketsCount = 0;

    @Column(name = "avg_journey_distance", precision = 5, scale = 2)
    private BigDecimal avgJourneyDistance = BigDecimal.ZERO;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "update_at")
    private LocalDateTime updateAt;

    public DailyAnalytics() {
    }

    public DailyAnalytics(Long id, LocalDate reportDate, Integer totalTicketsSold, BigDecimal totalRevenue, Integer totalPassengers, Integer peakHourTickets, Integer offPeakTickets, Integer singleJourneyCount, Integer passTicketsCount, Integer oneDayTicketsCount, Integer threeDayTicketsCount, Integer weekTicketsCount, Integer monthTicketsCount, Integer studentTicketsCount, BigDecimal avgJourneyDistance, LocalDateTime createAt, LocalDateTime updateAt) {
        this.id = id;
        this.reportDate = reportDate;
        this.totalTicketsSold = totalTicketsSold;
        this.totalRevenue = totalRevenue;
        this.totalPassengers = totalPassengers;
        this.peakHourTickets = peakHourTickets;
        this.offPeakTickets = offPeakTickets;
        this.singleJourneyCount = singleJourneyCount;
        this.passTicketsCount = passTicketsCount;
        this.oneDayTicketsCount = oneDayTicketsCount;
        this.threeDayTicketsCount = threeDayTicketsCount;
        this.weekTicketsCount = weekTicketsCount;
        this.monthTicketsCount = monthTicketsCount;
        this.studentTicketsCount = studentTicketsCount;
        this.avgJourneyDistance = avgJourneyDistance;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getReportDate() {
        return reportDate;
    }

    public void setReportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
    }

    public Integer getTotalTicketsSold() {
        return totalTicketsSold;
    }

    public void setTotalTicketsSold(Integer totalTicketsSold) {
        this.totalTicketsSold = totalTicketsSold;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public Integer getTotalPassengers() {
        return totalPassengers;
    }

    public void setTotalPassengers(Integer totalPassengers) {
        this.totalPassengers = totalPassengers;
    }

    public Integer getPeakHourTickets() {
        return peakHourTickets;
    }

    public void setPeakHourTickets(Integer peakHourTickets) {
        this.peakHourTickets = peakHourTickets;
    }

    public Integer getOffPeakTickets() {
        return offPeakTickets;
    }

    public void setOffPeakTickets(Integer offPeakTickets) {
        this.offPeakTickets = offPeakTickets;
    }

    public Integer getSingleJourneyCount() {
        return singleJourneyCount;
    }

    public void setSingleJourneyCount(Integer singleJourneyCount) {
        this.singleJourneyCount = singleJourneyCount;
    }

    public Integer getPassTicketsCount() {
        return passTicketsCount;
    }

    public void setPassTicketsCount(Integer passTicketsCount) {
        this.passTicketsCount = passTicketsCount;
    }

    public Integer getOneDayTicketsCount() {
        return oneDayTicketsCount;
    }

    public void setOneDayTicketsCount(Integer oneDayTicketsCount) {
        this.oneDayTicketsCount = oneDayTicketsCount;
    }

    public Integer getThreeDayTicketsCount() {
        return threeDayTicketsCount;
    }

    public void setThreeDayTicketsCount(Integer threeDayTicketsCount) {
        this.threeDayTicketsCount = threeDayTicketsCount;
    }

    public Integer getWeekTicketsCount() {
        return weekTicketsCount;
    }

    public void setWeekTicketsCount(Integer weekTicketsCount) {
        this.weekTicketsCount = weekTicketsCount;
    }

    public Integer getMonthTicketsCount() {
        return monthTicketsCount;
    }

    public void setMonthTicketsCount(Integer monthTicketsCount) {
        this.monthTicketsCount = monthTicketsCount;
    }

    public Integer getStudentTicketsCount() {
        return studentTicketsCount;
    }

    public void setStudentTicketsCount(Integer studentTicketsCount) {
        this.studentTicketsCount = studentTicketsCount;
    }

    public BigDecimal getAvgJourneyDistance() {
        return avgJourneyDistance;
    }

    public void setAvgJourneyDistance(BigDecimal avgJourneyDistance) {
        this.avgJourneyDistance = avgJourneyDistance;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }
}

