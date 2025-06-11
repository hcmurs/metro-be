package com.example.cronjob.DTO.Response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class DailyAnalyticsResponse {
    private Long analyticsId;
    private LocalDate reportDate;
    private Integer totalTicketsSold;
    private BigDecimal totalRevenue;
    private Integer totalPassengers;
    private Integer peakHourTickets;
    private Integer offPeakTickets;
    private Integer singleJourneyCount;
    private Integer passTicketsCount;
    private Integer oneDayTicketsCount;
    private Integer threeDayTicketsCount;
    private Integer weekTicketsCount;
    private Integer monthTicketsCount;
    private Integer studentTicketsCount;
    private BigDecimal avgJourneyDistance;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    public Long getAnalyticsId() {
        return analyticsId;
    }

    public void setAnalyticsId(Long analyticsId) {
        this.analyticsId = analyticsId;
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

    public DailyAnalyticsResponse(Long analyticsId, LocalDate reportDate, Integer totalTicketsSold, BigDecimal totalRevenue, Integer totalPassengers, Integer peakHourTickets, Integer offPeakTickets, Integer singleJourneyCount, Integer passTicketsCount, Integer oneDayTicketsCount, Integer threeDayTicketsCount, Integer weekTicketsCount, Integer monthTicketsCount, Integer studentTicketsCount, BigDecimal avgJourneyDistance, LocalDateTime createAt, LocalDateTime updateAt) {
        this.analyticsId = analyticsId;
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

    public DailyAnalyticsResponse() {
    }
}

