package com.ecommerce.staples_clone.mongodb.service;

import com.ecommerce.staples_clone.exception.ResourceNotFoundException;
import com.ecommerce.staples_clone.mongodb.model.MongoCustomer;
import com.ecommerce.staples_clone.mongodb.repository.MongoCustomerRepository;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MongoCustomerService {
  private static final Logger log = LoggerFactory.getLogger(MongoCustomerService.class);

  private final MongoCustomerRepository customerRepository;

  @Autowired
  public MongoCustomerService(MongoCustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  public List<MongoCustomer> getAllCustomers() {
    log.info("Fetching all customers from MongoDB.");
    List<MongoCustomer> customers = customerRepository.findAll();
    log.info("Found {} customers.", customers.size());
    return customers;
  }

  public MongoCustomer getCustomerByCustomerId(String customerId) {
    log.debug("Querying MongoDB for customer with customerId: {}", customerId);
    return customerRepository
        .findByCustomerId(customerId)
        .orElseThrow(
            () -> {
              log.warn("Failed to find customer with customerId: {}", customerId);
              return new ResourceNotFoundException(
                  "Customer not found with customerId: " + customerId);
            });
  }

  public MongoCustomer createCustomer(MongoCustomer customer) {
    log.debug("Attempting to create a new customer in MongoDB with email: {}", customer.getEmail());
    customer.setCustomerId("CUST" + UUID.randomUUID().toString().substring(0, 5).toUpperCase());
    customer.setCreatedDate(OffsetDateTime.now());
    MongoCustomer savedCustomer = customerRepository.save(customer);
    log.info(
        "Successfully created new customer with customerId: {}", savedCustomer.getCustomerId());
    return savedCustomer;
  }

  public MongoCustomer updateCustomer(String customerId, MongoCustomer updatedCustomerDetails) {
    log.debug("Attempting to update customer with customerId: {}", customerId);
    MongoCustomer existingCustomer = getCustomerByCustomerId(customerId);

    updatedCustomerDetails.setId(existingCustomer.getId());
    updatedCustomerDetails.setCustomerId(existingCustomer.getCustomerId());

    MongoCustomer updatedCustomer = customerRepository.save(updatedCustomerDetails);
    log.info("Successfully updated customer with customerId: {}", updatedCustomer.getCustomerId());
    return updatedCustomer;
  }

  public void deleteCustomer(String customerId) {
    log.debug("Attempting to delete customer with customerId: {}", customerId);
    MongoCustomer customer = getCustomerByCustomerId(customerId); // Reuse the find-or-throw method
    customerRepository.delete(customer);
    log.info("Successfully deleted customer with customerId: {}", customerId);
  }
}
