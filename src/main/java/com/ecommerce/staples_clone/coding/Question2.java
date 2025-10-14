package com.ecommerce.staples_clone.coding;

import java.util.*;
import java.util.stream.Collectors;

public class Question2 {

  public static void main(String[] args) {
    // Given a list of integers, find the second-highest number using the Stream API.

    List<Integer> myList1 = Arrays.asList(10, 90, 20, 30, 50);

    // prints the highest number.
    int highestNo = 0;
    for (int num : myList1) {
      if (num > highestNo) {
        highestNo = num;
      }
    }
    System.out.println(highestNo);

    // 2.
    List<Integer> myList2 = Arrays.asList(10, 90, 20, 30, 50);
    Collections.sort(myList2);
    System.out.println(myList2);

    // 3.
    List<Integer> myList3 = Arrays.asList(10, 90, 20, 30, 50);
    myList3.sort(null);
    System.out.println(myList3);

    // 4.
    List<Integer> myList4 = Arrays.asList(10, 90, 20, 30, 50);
    List<Integer> newList = myList4.stream().sorted().toList();
    System.out.println(newList);

    List<Integer> myList5 = Arrays.asList(10, 90, 20, 30, 50);
    Optional<Integer> newList2 =
        myList5.stream().distinct().sorted(Comparator.reverseOrder()).skip(1).findFirst();
    System.out.println(newList2.get());
  }
}
