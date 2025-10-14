package com.ecommerce.staples_clone.coding;

import java.util.*;

public class Question8 {
  public static void main(String[] args) {
    // Write a program to count the occurrences of each word in a given sentence.

    String myText = "This is the best place in the world".trim().toLowerCase();
    String[] arr = myText.split(" ");

    Map<String, Integer> store = new HashMap<>();

    for (String str : arr) {
      store.put(str, store.getOrDefault(str, 0) + 1);
    }

    System.out.println(store);
  }
}
