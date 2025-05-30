package com.example.stationservice.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Table(name = "routes")
@Data
public class Routes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long routeId;
    private String routeName;
    private String routeCode;
    private float distanceInKm;
    private int quantityOfStations;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // Additional fields can be added as needed
}
