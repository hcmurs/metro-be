package org.alfred.ticketservice.repository;

import org.alfred.ticketservice.model.FareMatrix;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FareMatrixRepository extends JpaRepository<FareMatrix, Long> {
    FareMatrix findByStartStationIdAndEndStationIdAndIsActiveTrue(Long startStationId, Long endStationId);
    @Query("SELECT fm FROM FareMatrix fm WHERE (fm.startStationId = :stationId OR fm.endStationId = :stationId)")
    List<FareMatrix> findByStationId(@Param("stationId") Long stationId);
    List<FareMatrix> findByStartStationIdAndIsActiveTrue(Long startStationId);
}
