package com.tnite.jobwinner.jobs;

import com.tnite.jobwinner.service.InterviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SetOutdatedInterviewToExpiredJob {

    @Autowired
    private final InterviewService interviewService;

    @Scheduled(cron = "0 0 1 * * ?") // Runs every night at 1:00 AM
    public void updateOutdatedInterviewToExpired() {
        log.info("Running scheduled job to set outdated interview to expired");
        interviewService.updateOutdatedInterviewToExpired()
            .subscribe(); // Executes the reactive operation
    }
}
