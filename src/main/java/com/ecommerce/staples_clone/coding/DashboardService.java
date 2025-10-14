package com.ecommerce.staples_clone.coding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

// Dummy DTOs for the example
record UserProfile(String userId, String name) {}
record Order(String orderId, String details) {}
record ShippingStatus(String orderId, String status) {}
record DashboardDTO(UserProfile profile, List<Order> orders, ShippingStatus status) {}

record Animal(String id){}

@Service
public class DashboardService {

  private static final Logger logger = LoggerFactory.getLogger(DashboardService.class);

  // Simulates calling an external user service
  @Async("taskExecutor")
  public CompletableFuture<UserProfile> getUserProfile(String userId) {
    logger.info("Fetching user profile for user {}...", userId);
    try {
      // Simulate network latency
      TimeUnit.MILLISECONDS.sleep(500);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
    logger.info("User profile fetched.");
    return CompletableFuture.completedFuture(new UserProfile(userId, "John Doe"));
  }

  // Simulates calling an external order service
  @Async("taskExecutor")
  public CompletableFuture<List<Order>> getRecentOrders(String userId) {
    logger.info("Fetching orders for user {}...", userId);
    try {
      TimeUnit.MILLISECONDS.sleep(800); // This one is slower
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
    logger.info("Orders fetched.");
    return CompletableFuture.completedFuture(List.of(new Order("order123", "Laptop")));
  }

  // Simulates calling an external shipping service
  @Async("taskExecutor")
  public CompletableFuture<ShippingStatus> getShippingStatus(String userId) {
    logger.info("Fetching shipping status for user {}...", userId);
    try {
      TimeUnit.MILLISECONDS.sleep(300);
      // Uncomment the line below to test the failure scenario
      // throw new RuntimeException("Shipping service unavailable");
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
    logger.info("Shipping status fetched.");
    return CompletableFuture.completedFuture(new ShippingStatus("order123", "In Transit"));
  }
}
