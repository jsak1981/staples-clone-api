package com.ecommerce.staples_clone.service;

import com.ecommerce.staples_clone.dto.LoginRequestDTO;
import com.ecommerce.staples_clone.model.Customer;
import com.ecommerce.staples_clone.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

  private final CustomerRepository customerRepository;
  private final PasswordEncoder passwordEncoder;
  private static final Logger log = LoggerFactory.getLogger(AuthService.class);

  public AuthService(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
    this.customerRepository = customerRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public boolean login(LoginRequestDTO loginRequest) {
    log.debug("Attempting login for user: {}", loginRequest.getEmail());
    Customer customer = customerRepository.findByEmail(loginRequest.getEmail()).orElse(null);

    if (customer == null) {
      log.warn("Login failed: No user found with email: {}", loginRequest.getEmail());
      return false;
    }

    boolean passwordMatches =
        passwordEncoder.matches(loginRequest.getPassword(), customer.getPassword());
    if (passwordMatches) {
      log.info("Login successful for user: {}", loginRequest.getEmail());
    } else {
      log.warn("Login failed: Invalid password for user: {}", loginRequest.getEmail());
    }
    return passwordMatches;
  }
}
