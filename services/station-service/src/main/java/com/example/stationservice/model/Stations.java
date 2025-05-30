package com.example.stationservice.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "stations")
@Data
public class Stations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "station_id")
    private Long stationId;
    @Column(name = "route_id")
    private Integer routeId;
    @Column(name = "station_code")
    private Integer stationCode;
    private String name;
    private String address;
    private BigDecimal latitude;
    private BigDecimal longitude;
    @Column(name = "sequence_order")
    private Integer sequenceOrder;

    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Column(name="updated_at")
    private Timestamp updatedAt;

    public enum Status {
        open, closed
    }
}
