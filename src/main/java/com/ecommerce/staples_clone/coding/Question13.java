package com.ecommerce.staples_clone.coding;

import java.util.*;

public class Question13 {

  public static void main(String[] args) {
    System.out.println("Start");
    // Given an array containing \(n\) distinct numbers taken from \(0,1,2,...,n\), find the one
    // that is missing from the array.

    List<Integer> myList = Arrays.asList(0, 1, 2, 3, 4, 5, 7, 1);
    // Set<Integer> store = new LinkedHashSet<>();

    int expTerm = 0;

    for (int i : myList) {
      int temp = i;
      if (temp != expTerm) {
        System.out.println(expTerm);
      }
      expTerm++;
    }

    // System.out.println(store);

  }
}
