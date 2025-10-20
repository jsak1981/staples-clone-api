package com.ecommerce.staples_clone.mongodb.controller;

import com.ecommerce.staples_clone.mongodb.model.MongoCustomer;
import com.ecommerce.staples_clone.mongodb.service.MongoCustomerService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mongo/customers")
public class MongoCustomerController {
  private static final Logger log = LoggerFactory.getLogger(MongoCustomerController.class);

  private final MongoCustomerService customerService;

  @Autowired
  public MongoCustomerController(MongoCustomerService s) {
    this.customerService = s;
  }

  @GetMapping
  public ResponseEntity<List<MongoCustomer>> getAllCustomers() {
    log.info("Received request to get all MongoDB customers.");
    List<MongoCustomer> customers = customerService.getAllCustomers();
    return ResponseEntity.ok(customers);
  }

  @GetMapping("/{customerId}")
  public ResponseEntity<MongoCustomer> getCustomerById(@PathVariable String customerId) {
    log.info("Received request to get MongoDB customer by customerId: {}", customerId);
    MongoCustomer customer = customerService.getCustomerByCustomerId(customerId);
    return ResponseEntity.ok(customer);
  }

  @PostMapping
  public ResponseEntity<MongoCustomer> createCustomer(@RequestBody MongoCustomer customer) {
    log.info("Received request to create a new MongoDB customer.");
    MongoCustomer createdCustomer = customerService.createCustomer(customer);
    return new ResponseEntity<>(createdCustomer, HttpStatus.CREATED);
  }

  @PutMapping("/{customerId}")
  public ResponseEntity<MongoCustomer> updateCustomer(
      @PathVariable String customerId, @RequestBody MongoCustomer customer) {
    log.info("Received request to update MongoDB customer with customerId: {}", customerId);
    MongoCustomer updatedCustomer = customerService.updateCustomer(customerId, customer);
    return ResponseEntity.ok(updatedCustomer);
  }

  @DeleteMapping("/{customerId}")
  public ResponseEntity<Void> deleteCustomer(@PathVariable String customerId) {
    log.info("Received request to delete MongoDB customer with customerId: {}", customerId);
    customerService.deleteCustomer(customerId);
    return ResponseEntity.noContent().build();
  }
}
