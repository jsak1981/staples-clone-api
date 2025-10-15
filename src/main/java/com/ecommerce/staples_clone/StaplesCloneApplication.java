package com.ecommerce.staples_clone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class StaplesCloneApplication {

  public static void main(String[] args) {
    SpringApplication.run(StaplesCloneApplication.class, args);
  }
}
