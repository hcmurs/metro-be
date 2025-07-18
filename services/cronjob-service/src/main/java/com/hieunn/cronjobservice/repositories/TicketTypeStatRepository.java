package com.hieunn.cronjobservice.repositories;

import com.hieunn.cronjobservice.models.TicketTypeStatistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketTypeStatRepository extends JpaRepository<TicketTypeStatistic, Long> {
}
