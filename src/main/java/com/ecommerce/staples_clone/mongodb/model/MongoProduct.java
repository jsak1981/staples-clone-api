package com.ecommerce.staples_clone.mongodb.model;

import java.math.BigDecimal;
import java.util.*;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "products")
public class MongoProduct {

  @Id private String id;

  @Indexed(unique = true)
  @NotEmpty(message = "Product sku cannot be empty!.")
  @NotNull
  private String sku;

  private String name;
  private String description;
  private BigDecimal price;
  private Integer stock;
  private boolean onSale;
  private List<String> tags;
  private Specifications specifications;
  private List<Review> reviews;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getSku() {
    return sku;
  }

  public void setSku(String sku) {
    this.sku = sku;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Integer getStock() {
    return stock;
  }

  public void setStock(Integer stock) {
    this.stock = stock;
  }

  public boolean isOnSale() {
    return onSale;
  }

  public void setOnSale(boolean onSale) {
    this.onSale = onSale;
  }

  public List<String> getTags() {
    return tags;
  }

  public void setTags(List<String> tags) {
    this.tags = tags;
  }

  public Specifications getSpecifications() {
    return specifications;
  }

  public void setSpecifications(Specifications specifications) {
    this.specifications = specifications;
  }

  public List<Review> getReviews() {
    return reviews;
  }

  public void setReviews(List<Review> reviews) {
    this.reviews = reviews;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }
}
