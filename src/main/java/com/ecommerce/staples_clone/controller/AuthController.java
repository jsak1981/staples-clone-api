package com.ecommerce.staples_clone.controller;

import com.ecommerce.staples_clone.dto.AuthRequestDTO;
import com.ecommerce.staples_clone.dto.AuthResponseDTO;
import com.ecommerce.staples_clone.jwt.JwtUtil;
import com.ecommerce.staples_clone.dto.LoginRequestDTO;
import com.ecommerce.staples_clone.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

// AuthController.java
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
  private static final Logger log = LoggerFactory.getLogger(AuthController.class);
  private final AuthService authService;

  @Autowired private AuthenticationManager authenticationManager;

  @Autowired private UserDetailsService userDetailsService;

  @Autowired private JwtUtil jwtUtil;

  public AuthController(AuthService a) {
    this.authService = a;
  }

  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody LoginRequestDTO loginDto) {
    log.info("Received login request for email: {}", loginDto.getEmail());
    authService.login(loginDto);
    return ResponseEntity.ok("Login successful");
  }

  @PostMapping("/authenticate")
  public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequestDTO authRequest)
      throws Exception {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            authRequest.getUsername(), authRequest.getPassword()));

    final UserDetails userDetails =
        userDetailsService.loadUserByUsername(authRequest.getUsername());
    final String jwt = jwtUtil.generateToken(userDetails);

    return ResponseEntity.ok(new AuthResponseDTO(jwt));
  }
}
