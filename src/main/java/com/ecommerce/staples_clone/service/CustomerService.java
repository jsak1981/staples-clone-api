package com.ecommerce.staples_clone.service;

import com.ecommerce.staples_clone.dto.CustomerRequestDTO;
import com.ecommerce.staples_clone.model.Customer;
import com.ecommerce.staples_clone.repository.CustomerRepository;
import java.util.*;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService {
  private static final Logger log = LoggerFactory.getLogger(CustomerService.class);
  private final CustomerRepository customerRepository;

  @Autowired
  public CustomerService(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
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
  public Customer createCustomer(CustomerRequestDTO customerDTO) {
    Customer customer = new Customer();
    customer.setFirstName(customerDTO.getFirstName());
    customer.setLastName(customerDTO.getLastName());
    customer.setEmail(customerDTO.getEmail());
    //customer.setPasswordHash(customerDTO.getPasswordHash());
    customer.setPasswordHash("default_password_hash_placeholder");

    Customer savedCustomer = customerRepository.save(customer);
    log.info("Successfully created customer with id: {}", savedCustomer);
    return savedCustomer;
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
