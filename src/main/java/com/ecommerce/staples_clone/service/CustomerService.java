package com.ecommerce.staples_clone.service;

import com.ecommerce.staples_clone.dto.ChangePasswordDTO;
import com.ecommerce.staples_clone.dto.CustomerRequestDTO;
import com.ecommerce.staples_clone.model.Customer;
import com.ecommerce.staples_clone.repository.CustomerRepository;
import java.util.*;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService {
  private static final Logger log = LoggerFactory.getLogger(CustomerService.class);
  private final CustomerRepository customerRepository;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public CustomerService(CustomerRepository c, PasswordEncoder p) {
    this.customerRepository = c;
    this.passwordEncoder = p;
  }

  @Transactional(readOnly = true)
  public List<Customer> getAllCustomers() {
    log.info("Fetching all customers");
    return customerRepository.findAll();
  }

  @Transactional(readOnly = true)
  public Optional<Customer> getCustomerById(Long id) {
    log.info("Fetching customer with id: {}", id);
    return customerRepository.findById(id);
  }

  @Transactional
  public Customer createCustomer(CustomerRequestDTO requestDTO) {
    log.debug("Attempting to create a new customer with email: {}", requestDTO.getEmail());

    boolean emailFound = customerRepository.findByEmail(requestDTO.getEmail()).isPresent();
    if (emailFound) {
      throw new IllegalStateException("Email already in use");
    }
    Customer customer = new Customer();
    customer.setFirstName(requestDTO.getFirstName());
    customer.setLastName(requestDTO.getLastName());
    customer.setEmail(requestDTO.getEmail());

    String hashedPassword = passwordEncoder.encode(requestDTO.getPassword());
    customer.setPassword(hashedPassword);

    Customer savedCustomer = customerRepository.save(customer);
    log.info("Successfully created customer with id: {}", savedCustomer);
    return savedCustomer;
  }

  @Transactional
  public boolean changePassword(Long customerId, ChangePasswordDTO changePasswordDTO) {
    log.debug("Attempting to change password for customer ID: {}", customerId);
    Customer customer =
        customerRepository
            .findById(customerId)
            .orElseThrow(() -> new RuntimeException("Customer not found with id: " + customerId));

    // 1. Verify the old password
    if (!passwordEncoder.matches(changePasswordDTO.getOldPassword(), customer.getPassword())) {
      log.warn("Password change failed for customer ID: {}. Incorrect old password.", customerId);
      return false;
    }

    // 2. Hash and set the new password
    String newHashedPassword = passwordEncoder.encode(changePasswordDTO.getNewPassword());
    customer.setPassword(newHashedPassword);
    customerRepository.save(customer);

    log.info("Successfully changed password for customer ID: {}", customerId);
    return true;
  }

  @Transactional
  public Optional<Customer> updateCustomer(Long id, CustomerRequestDTO cusDto) {
    log.info("Attempting to update customer with id: {}", id);
    return customerRepository
        .findById(id)
        .map(
            customer -> {
              customer.setFirstName(cusDto.getFirstName());
              customer.setLastName(cusDto.getLastName());
              customer.setEmail(cusDto.getEmail());
              return customerRepository.save(customer);
            });
  }

  @Transactional
  public boolean deleteCustomer(Long id) {
    if (customerRepository.existsById(id)) {
      customerRepository.deleteById(id);
      log.info("Successfully deleted customer with id: {}", id);
      return true;
    }
    log.warn("Could not delete. Customer not found with id: {}", id);
    return false;
  }
}
