package com.ecommerce.staples_clone.dto;

import java.util.*;

public class OrderRequestDTO {

  private Long customerId;
  private List<OrderItemDTO> items;

  public Long getCustomerId() {
    return customerId;
  }

  public void setCustomerId(Long customerId) {
    this.customerId = customerId;
  }

  public List<OrderItemDTO> getItems() {
    return items;
  }

  public void setItems(List<OrderItemDTO> items) {
    this.items = items;
  }

  // Inner public class...
  public static class OrderItemDTO {
    private Long productId;
    private Integer quantity;

    public Long getProductId() {
      return productId;
    }

    public void setProductId(Long productId) {
      this.productId = productId;
    }

    public Integer getQuantity() {
      return quantity;
    }

    public void setQuantity(Integer quantity) {
      this.quantity = quantity;
    }
  }
}
