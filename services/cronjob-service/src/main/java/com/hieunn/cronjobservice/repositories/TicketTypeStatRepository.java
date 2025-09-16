/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: Cronjob-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package com.hieunn.cronjobservice.repositories;

import com.hieunn.cronjobservice.models.TicketTypeStatistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketTypeStatRepository extends JpaRepository<TicketTypeStatistic, Long> {}
