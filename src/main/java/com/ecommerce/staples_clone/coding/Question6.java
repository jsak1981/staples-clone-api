package com.ecommerce.staples_clone.coding;

import java.util.*;

public class Question6 {
  public static void main(String[] args) {
    // Find the first non-repeated character in a given string.
    System.out.println("start");
    String myText = "This is the best place in the world";

    // char[] charStrArray = myText.replace("\\s", "").toLowerCase().toCharArray();

    Map<Character, Integer> store = new LinkedHashMap<>();

    for (char ch : myText.replaceAll("\\s", "").toLowerCase().toCharArray()) {
      store.put(ch, store.getOrDefault(ch, 0) + 1);
    }
    System.out.println(store);

    for(char ch :  store.keySet()){
      if(store.get(ch) == 1){
        System.out.println(ch);
        break;
      }
    }


  }
}
