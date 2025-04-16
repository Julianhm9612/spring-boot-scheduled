package com.example.spring_boot_scheduled;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {
    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    public static final DateFormat getDateTimeFormat() {
        return new SimpleDateFormat("HH:mm:ss");
    }

    // Tasks where order matters (e.g., batch processing).
    @Scheduled(fixedDelay = 5000) // 5 seconds - Executes the method with a fixed period between the end of the last invocation and the start of the next.
    public void fixedDelay() {
        if (log.isInfoEnabled()) {
            log.info("Fixed Delay - The time is now {}", getDateTimeFormat().format(new Date()));
        }
    }

    // Independent tasks (e.g., polling an API).
    @Scheduled(fixedRate = 3000) // 3 seconds - Executes the method at fixed intervals, regardless of when the previous execution finished.
    public void fixedRate() {
        if (log.isInfoEnabled()) {
            log.info("Fixed Rate - The time is now {}", getDateTimeFormat().format(new Date()));
        }
    }

    // Time-based scheduling (e.g., “Every day at midnight”)
    @Scheduled(cron = "0 15 10 * * ?") // 10:15 AM every day
    public void doTask() {
        if (log.isInfoEnabled()) {
            log.info("Cron - The time is now {}", getDateTimeFormat().format(new Date()));
        }
    }

    @Scheduled(fixedRate = 30000, initialDelay = 30000) // Wait 30s, then run every 30s
    public void syncWithExternalAPI() {
        if (log.isInfoEnabled()) {
            log.info("Initial Delay - The time is now {}", getDateTimeFormat().format(new Date())); // Avoids running immediately on startup
        }
    }

    @Scheduled(cron = "0 0 9 * * ?", zone = "Europe/Paris") // 9 AM Paris time
    public void sendRegionalReports() {
        if (log.isInfoEnabled()) {
            log.info("zone - The time is now {}", getDateTimeFormat().format(new Date()));
        }
    }

    @Scheduled(fixedRateString = "${invoice.generate.interval}")
    public void generateInvoices() {
        if (log.isInfoEnabled()) {
            log.info("Fixed Rate String - The time is now {}", getDateTimeFormat().format(new Date()));
        }
    }

    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(5); // 5 threads
        return scheduler;
    }

    @Async
    @Scheduled(fixedRate = 1000)
    public void runInParallel() {
        if (log.isInfoEnabled()) {
            log.info("Async - The time is now {}", getDateTimeFormat().format(new Date()));
        }
    }

    @Scheduled(fixedRate = 5000)
    public void safeTask() {
        try {
            // Task logic
        } catch (Exception e) {
            log.error("Task failed", e);
        }
    }

    // public void rescheduleTask(long newInterval) {
    //     registrar.addFixedRateTask(() -> myTask(), newInterval);
    // }

}
