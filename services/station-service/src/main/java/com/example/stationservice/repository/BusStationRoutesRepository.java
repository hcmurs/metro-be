package com.example.stationservice.repository;

import com.example.stationservice.model.BusStationRoutes;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Repository
public interface BusStationRoutesRepository extends MongoRepository<BusStationRoutes,String> {
    List<BusStationRoutes> findByStationId(String stationId);
}
