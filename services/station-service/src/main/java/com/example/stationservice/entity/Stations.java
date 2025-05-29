package com.example.stationservice.entity;

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
    private Integer stationId;

    private Integer routeId;
    @Column(unique = true)
    private Integer stationCode;

    private String name;
    private String address;
    private BigDecimal latitude;
    private BigDecimal longitude;

    private Integer sequenceOrder;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Timestamp createdAt;
    private Timestamp updatedAt;

    public enum Status {
        open, closed
    }
}
