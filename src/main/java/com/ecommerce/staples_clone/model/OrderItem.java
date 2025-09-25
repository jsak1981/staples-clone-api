package com.ecommerce.staples_clone.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.math.BigDecimal;

// Add this annotation to ignore Hibernate's lazy loading proxies
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "order_items")
public class OrderItem {

  @EmbeddedId private OrderItemId id = new OrderItemId();

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("orderId")
  @JoinColumn(name = "order_id")
  @JsonBackReference
  private Order order;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("productId")
  @JoinColumn(name = "product_id")
  private Product product;

  @Column(name = "quantity", nullable = false)
  private Integer quantity;

  @Column(name = "price_at_purchase", nullable = false)
  private BigDecimal priceAtPurchase;

  public OrderItemId getId() {
    return id;
  }

  public void setId(OrderItemId id) {
    this.id = id;
  }

  public Order getOrder() {
    return order;
  }

  public void setOrder(Order order) {
    this.order = order;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public BigDecimal getPriceAtPurchase() {
    return priceAtPurchase;
  }

  public void setPriceAtPurchase(BigDecimal priceAtPurchase) {
    this.priceAtPurchase = priceAtPurchase;
  }
}
