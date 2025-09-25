package com.ecommerce.staples_clone.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

// Add this annotation to ignore Hibernate's lazy loading proxies
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "orders")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "order_id")
  private Long orderId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "customer_id", nullable = false)
  @JsonBackReference // Child side
  private Customer customer;

  @Column(name = "order_date")
  private final Instant orderDate = Instant.now();

  @Column(name = "status", nullable = false)
  private String status;

  @Column(name = "total_amount", nullable = false)
  private BigDecimal totalAmount;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference // Parent side
  private Set<OrderItem> items = new HashSet<>();

  public void addItem(OrderItem item) {
    items.add(item);
    item.setOrder(this);
  }

  public Long getOrderId() {
    return orderId;
  }

  public void setOrderId(Long orderId) {
    this.orderId = orderId;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public Instant getOrderDate() {
    return orderDate;
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

  public Set<OrderItem> getItems() {
    return items;
  }

  public void setItems(Set<OrderItem> items) {
    this.items = items;
  }
}
