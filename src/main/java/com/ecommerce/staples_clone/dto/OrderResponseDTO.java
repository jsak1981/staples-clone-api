package com.ecommerce.staples_clone.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;

public class OrderResponseDTO {
  private Long orderId;
  private Instant orderDate;
  private String status;
  private BigDecimal totalAmount;
  private Set<OrderItemResponseDTO> items;

  public Long getOrderId() {
    return orderId;
  }

  public void setOrderId(Long orderId) {
    this.orderId = orderId;
  }

  public Instant getOrderDate() {
    return orderDate;
  }

  public void setOrderDate(Instant orderDate) {
    this.orderDate = orderDate;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public BigDecimal getTotalAmount() {
    return totalAmount;
  }

  public void setTotalAmount(BigDecimal totalAmount) {
    this.totalAmount = totalAmount;
  }

  public Set<OrderItemResponseDTO> getItems() {
    return items;
  }

  public void setItems(Set<OrderItemResponseDTO> items) {
    this.items = items;
  }
}
