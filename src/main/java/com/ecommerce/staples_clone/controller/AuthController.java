package com.ecommerce.staples_clone.controller;

import com.ecommerce.staples_clone.dto.LoginRequestDTO;
import com.ecommerce.staples_clone.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
  private static final Logger log = LoggerFactory.getLogger(AuthController.class);
  private final AuthService authService;

  public AuthController(AuthService a) {
    this.authService = a;
  }

  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody LoginRequestDTO loginDto) {

    if (authService.login(loginDto)) {
      return ResponseEntity.ok("Login successful");
    } else {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }
  }
}
