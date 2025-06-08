package com.example.cronjob.Repository;

import com.example.cronjob.Pojos.DailyAnalytics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyAnalyticsRepository extends JpaRepository<DailyAnalytics, Long> {

}
