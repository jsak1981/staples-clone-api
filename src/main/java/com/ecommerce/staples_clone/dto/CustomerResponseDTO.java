package com.ecommerce.staples_clone.dto;

import java.util.*;

public class CustomerResponseDTO {
  private Long customerId;
  private String firstName;
  private String lastName;
  private String email;
  private Set<OrderResponseDTO> orders; // Includes the list of orders for this customer

  public Long getCustomerId() {
    return customerId;
  }

  public void setCustomerId(Long customerId) {
    this.customerId = customerId;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Set<OrderResponseDTO> getOrders() {
    return orders;
  }

  public void setOrders(Set<OrderResponseDTO> orders) {
    this.orders = orders;
  }
}
