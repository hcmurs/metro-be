/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: Station-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package com.example.stationservice.repository;

import com.example.stationservice.model.Routes;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoutesRepository extends JpaRepository<Routes, Long> {
  // Additional query methods can be defined here if needed
  Boolean existsByRouteCode(String routeCode);

  List<Routes> findByRouteNameContainingIgnoreCase(String name);

  Optional<Routes> findByRouteCode(String routeCode);
}
