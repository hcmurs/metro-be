package com.example.stationservice.repository;

import com.example.stationservice.model.BusStationRoutes;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusStationRoutesRepository extends MongoRepository<BusStationRoutes,String> {
    List<BusStationRoutes> findByStationId(String stationId);
}
