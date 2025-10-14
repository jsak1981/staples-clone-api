package com.ecommerce.staples_clone.coding;

import java.util.*;

public class ComparatorExample {

  public static void main(String[] args) {
    List<Product> products = new ArrayList<>();
    products.add(new Product("Laptop", 1200.00));
    products.add(new Product("Mouse", 25.50));
    products.add(new Product("Keyboard", 75.00));

    /*products.sort(
        (p1, p2) -> {
          return Double.compare(p1.getPrice(), p2.getPrice());
        });
    products.forEach(System.out::println);*/

    /*products.sort(Comparator.comparing(p1 -> {
    	return p1.getName();
    }));

       products.forEach(p -> System.out.println(p));*/

    // List<Double> myList = Collections.empty

    Object[] temp = {10, 20, 30};
    System.out.println(Arrays.deepToString(temp));
  }
}

class Product {
  private String name;
  private double price;

  public Product(String name, double price) {
    this.name = name;
    this.price = price;
  }

  public String getName() {
    return name;
  }

  public double getPrice() {
    return price;
  }

  @Override
  public String toString() {
    return "Product{" + "name='" + name + '\'' + ", price=" + price + '}';
  }
}
