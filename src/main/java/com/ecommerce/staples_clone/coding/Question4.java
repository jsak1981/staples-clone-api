package com.ecommerce.staples_clone.coding;

import java.util.*;

public class Question4 {
  public static void main(String[] args) {
    // How do you find all duplicate characters in a string and print their count using the Stream
    // API?
    String myText = "This is a Boson city";
    Map<Character, Integer> store = new HashMap<>();

    for (char ch : myText.toCharArray()) {
      store.put(ch, store.getOrDefault(ch, 0) + 1);
    }

    System.out.println(store);
  }
}
