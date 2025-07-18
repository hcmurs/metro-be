package com.hieunn.cronjobservice.repositories;

import com.hieunn.cronjobservice.models.HourUsageStatistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HourUsageStatRepository extends JpaRepository<HourUsageStatistic, Long> {
}
