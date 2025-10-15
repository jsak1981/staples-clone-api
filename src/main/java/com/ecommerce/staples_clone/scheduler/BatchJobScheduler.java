package com.ecommerce.staples_clone.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BatchJobScheduler {
  private static final Logger log = LoggerFactory.getLogger(BatchJobScheduler.class);

  @Autowired
  @Qualifier("importProductJob")
  private Job importProductJob;

  @Autowired private JobLauncher jobLauncher;

  /**
   * This method is scheduled to run a cron job. Cron expression: "0 0 21 * * ?" means: - 0: at the
   * 0th second - 0: at the 0th minute - 21: at the 21st hour (9 PM) - *: every day of the month -
   * *: every month - ?: any day of the week
   *
   * <p>The zone attribute is crucial for handling time zones correctly, especially with Daylight
   * Saving Time.
   */
  @Scheduled(cron = "0 0 22 * * ?", zone = "America/New_York")
  public void runProductImportJob() {
    log.info("Scheduler starting the product batch job...");
    try {
      // common code.
      JobParameters jobParameters =
          new JobParametersBuilder()
              .addLong("startAt", System.currentTimeMillis())
              .toJobParameters();

      jobLauncher.run(importProductJob, jobParameters);
      // end.
      log.info("Product import batch job has been successfully launched by the scheduler.");
    } catch (Exception ex) {
      log.error("Schedular failed to start the product import job", ex);
    }
  }
}
