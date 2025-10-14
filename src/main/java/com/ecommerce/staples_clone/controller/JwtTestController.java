package com.ecommerce.staples_clone.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class JwtTestController {

  @GetMapping("/hello")
  public String hello() {
    return "Hello, World!";
  }

  @GetMapping("/admin/dashboard")
  @PreAuthorize("hasRole('ADMIN'")
  public String getAdminDashboard() {
    return "Welcome to the Admin Dashboard!";
  }
}
