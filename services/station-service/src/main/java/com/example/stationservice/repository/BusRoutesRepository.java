package com.example.stationservice.repository;

import com.example.stationservice.model.BusRoutes;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BusRoutesRepository extends MongoRepository<BusRoutes,String> {
    @NotNull Optional<BusRoutes> findById(@NotNull String id);
}
