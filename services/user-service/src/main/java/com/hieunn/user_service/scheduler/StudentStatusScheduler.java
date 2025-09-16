/**
 * Copyright (c) 2025 hcmurs. All rights reserved.
 *
 * Service: User-Service
 *
 * This software is the confidential and proprietary information of hcmurs.
 * You shall not disclose such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with hcmurs.
 */
package com.hieunn.user_service.scheduler;

import com.hieunn.user_service.models.User;
import com.hieunn.user_service.repositories.UserRepository;
import java.time.LocalDate;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class StudentStatusScheduler {
  UserRepository userRepository;

  @Scheduled(cron = "0 0 0 * * ?")
  @Transactional
  public void disableExpiredStudents() {
    List<User> users = userRepository.findAllByIsStudentTrue();
    LocalDate today = LocalDate.now();
    for (User user : users) {
      if (user.getStudentExpiredDate() != null && user.getStudentExpiredDate().isBefore(today)) {
        user.setStudent(false);
      }
    }
    userRepository.saveAll(users);
  }
}
