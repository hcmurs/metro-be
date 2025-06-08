package com.example.cronjob.Cronjob;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Cronjob {
    @Scheduled(cron = "${app.schedule.my-job}")
    public void runEvery15Second() {
        System.out.println("Chạy lúc 9h sáng mỗi ngày");
    }
}
