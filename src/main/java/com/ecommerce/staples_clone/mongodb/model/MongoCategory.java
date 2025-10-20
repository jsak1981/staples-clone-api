package com.ecommerce.staples_clone.mongodb.model;

import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "categories")
public class MongoCategory {

  @Id private String id;

  @Field(name = "categoryId")
  @Indexed(unique = true)
  @NotEmpty(message = "Category ID cannot be empty")
  private String categoryId;

  @NotEmpty(message = "Category name cannot be empty")
  private String name;

  private String description;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(String categoryId) {
    this.categoryId = categoryId;
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
}
