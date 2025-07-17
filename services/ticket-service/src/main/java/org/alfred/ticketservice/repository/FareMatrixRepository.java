package org.alfred.ticketservice.repository;

import java.util.List;
import org.alfred.ticketservice.model.FareMatrix;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FareMatrixRepository extends JpaRepository<FareMatrix, Long> {
    // Custom query methods can be defined here if needed
    FareMatrix findByStartStationIdAndEndStationId(Long startStationId, Long endStationId);
    List<FareMatrix> findByStartStationId(Long startStationId);
}
