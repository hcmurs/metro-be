/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: Ticket-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package org.alfred.ticketservice.repository;

import java.util.List;
import org.alfred.ticketservice.model.FareMatrix;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FareMatrixRepository extends JpaRepository<FareMatrix, Long> {
  FareMatrix findByStartStationIdAndEndStationIdAndIsActiveTrue(
      Long startStationId, Long endStationId);

  @Query(
      "SELECT fm FROM FareMatrix fm WHERE (fm.startStationId = :stationId OR fm.endStationId = :stationId)")
  List<FareMatrix> findByStationId(@Param("stationId") Long stationId);

  List<FareMatrix> findByStartStationIdAndIsActiveTrue(Long startStationId);
}
