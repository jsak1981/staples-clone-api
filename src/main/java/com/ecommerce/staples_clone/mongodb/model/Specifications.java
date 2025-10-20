package com.ecommerce.staples_clone.mongodb.model;

public class Specifications {

  private String type;
  private String color;
  private Integer ppm;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public Integer getPpm() {
    return ppm;
  }

  public void setPpm(Integer ppm) {
    this.ppm = ppm;
  }
}
