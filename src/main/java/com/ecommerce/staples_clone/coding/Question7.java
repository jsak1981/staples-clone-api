package com.ecommerce.staples_clone.coding;

import java.util.*;
import java.util.stream.Collectors;

public class Question7 {

  public static void main(String[] args) {
    // Given a list of numbers, how would you find all the numbers that start with the digit '2'
    // using Java 8 streams?

    List<Integer> myList = Arrays.asList(120, 201, 303, 148, 350, 176, 2);
    List<Integer> numbers = Arrays.asList(2, 220, 123, 456, 209, 321, 25);

    List<Integer> finalNumbers =
        numbers.stream()
            .map(String::valueOf)
            .filter(str -> ((String) str).startsWith("2"))
            .map(str -> Integer.valueOf((String) str))
            .collect(Collectors.toList());

    System.out.println(finalNumbers);
  }
}
