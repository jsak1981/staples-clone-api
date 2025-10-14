package com.ecommerce.staples_clone.service;

import com.ecommerce.staples_clone.dto.LoginRequestDTO;
import com.ecommerce.staples_clone.exception.AuthenticationFailedException;
import com.ecommerce.staples_clone.model.Customer;
import com.ecommerce.staples_clone.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

  private final CustomerRepository customerRepository;
  private final PasswordEncoder passwordEncoder;
  private static final Logger log = LoggerFactory.getLogger(AuthService.class);

  @Autowired
  public AuthService(CustomerRepository c, PasswordEncoder p) {
    this.customerRepository = c;
    this.passwordEncoder = p;
  }

  public void login(LoginRequestDTO loginRequest) {
    log.debug("Attempting login for user: {}", loginRequest.getEmail());
    Customer customer =
        customerRepository
            .findByEmail(loginRequest.getEmail())
            .orElseThrow(() -> new AuthenticationFailedException("Invalid credentials"));

	log.info("User provided password :" + loginRequest.getPassword());
	log.info("DB retrieved password :" + customer.getPassword());
    boolean passwordMatches =
        passwordEncoder.matches(loginRequest.getPassword(), customer.getPassword());
    if (!passwordMatches) {
      log.warn("Login failed: Invalid password for user: {}", loginRequest.getEmail());
      throw new AuthenticationFailedException("Invalid credentials");
    }
    log.info("Login successful for user: {}", loginRequest.getEmail());
  }
}
