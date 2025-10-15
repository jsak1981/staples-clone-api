package com.ecommerce.staples_clone.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/jobs")
public class JobTriggerController {

  private static final Logger log = LoggerFactory.getLogger(JobTriggerController.class);

  @Autowired private JobLauncher jobLauncher;

  @Autowired
  @Qualifier("importProductJob")
  private Job importProductJob;

  @PostMapping("/import-products")
  public ResponseEntity<String> triggerProductImportJob() {
    log.info("Received request to trigger product import job.");

    try {
      JobParameters jobParameters =
          new JobParametersBuilder()
              .addLong("startAt", System.currentTimeMillis())
              .toJobParameters();

      jobLauncher.run(importProductJob, jobParameters);
      return ResponseEntity.ok("Product import job has been started.");
    } catch (Exception ex) {
      log.error("Error starting product import job", ex);
      return ResponseEntity.status(500).body("Error starting job: " + ex.getMessage());
    }
  }
}
