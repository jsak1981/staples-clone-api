package com.ecommerce.staples_clone.coding;

import java.util.*;

public class Question12 {
  public static void main(String[] args) {
    System.out.println("started...");

    int firstTerm = 0;
    int secondTerm = 1;
    int totalCount = 0;

    while (totalCount < 10) {
      System.out.println(firstTerm);

      int nextTerm = firstTerm + secondTerm;

      firstTerm = secondTerm;
      secondTerm = nextTerm;
      totalCount++;
    }
  }
}
