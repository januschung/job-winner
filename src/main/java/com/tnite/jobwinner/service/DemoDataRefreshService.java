package com.tnite.jobwinner.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;

@Service
@Profile("demo")  // Only enable this service for the demo profile
@Slf4j
public class DemoDataRefreshService {

    private final DatabaseClient databaseClient;

    @Value("demo-data.sql")  // Path to your sample data file
    private ClassPathResource dataScript;

    public DemoDataRefreshService(DatabaseClient databaseClient) {
        log.info("Initialize DemoDataRefreshService");
        System.out.println(System.getProperty("java.class.path"));
        this.databaseClient = databaseClient;
    }

    // Scheduled task that runs every hour (on minute 0)
    @Scheduled(cron = "0 0 * * * ?")
    public void refreshData() {
        log.info("Refreshing demo data...");
        databaseClient.sql("SET REFERENTIAL_INTEGRITY FALSE;")
            .then()
            .subscribe();
        databaseClient.sql("TRUNCATE TABLE frequent_url RESTART IDENTITY;")
            .then()
            .subscribe();
        databaseClient.sql("TRUNCATE TABLE offer RESTART IDENTITY;")
            .then()
            .subscribe();
        databaseClient.sql("TRUNCATE TABLE interview RESTART IDENTITY;")
            .then()
            .subscribe();
        databaseClient.sql("TRUNCATE TABLE job_application RESTART IDENTITY;")
            .then()
            .subscribe();
        databaseClient.sql("SET REFERENTIAL_INTEGRITY TRUE;")
            .then()
            .subscribe();

        try {
            String script = new String(Files.readAllBytes(Paths.get(dataScript.getURI())));
            databaseClient.sql(script)
                .then()
                .subscribe();
            log.info("Refreshed demo data successfully!");
        } catch (Exception e) {
            log.error("Refreshing demo data with error: {}", e.getMessage());
        }
    }
}
