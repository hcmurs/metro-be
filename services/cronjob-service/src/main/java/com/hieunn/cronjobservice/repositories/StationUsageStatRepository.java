package com.hieunn.cronjobservice.repositories;

import com.hieunn.cronjobservice.models.StationUsageStatistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StationUsageStatRepository extends JpaRepository<StationUsageStatistic, Long> {
}
