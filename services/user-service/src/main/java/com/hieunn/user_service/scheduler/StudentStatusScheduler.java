package com.hieunn.user_service.scheduler;

import com.hieunn.user_service.models.User;
import com.hieunn.user_service.repositories.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

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
            if (user.getStudentExpiredDate() != null &&
                    user.getStudentExpiredDate().isBefore(today)) {
                user.setStudent(false);
            }
        }
        userRepository.saveAll(users);
    }
}
