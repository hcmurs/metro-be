package com.example.stationservice.repository;

import com.example.stationservice.model.BusStations;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusStationsRepository extends MongoRepository<BusStations,String> {
}
