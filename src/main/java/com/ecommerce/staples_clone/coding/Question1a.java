package com.ecommerce.staples_clone.coding;

import java.util.Collections;

public class Question1a {

  public static void main(String[] args) {
    String original = "This is the city of boston";
    char[] chArr = original.toCharArray();

    int left = 0;
    int right = original.length() - 1;

    while (right >= left) {
      char temp = chArr[left];
      chArr[left] = chArr[right];
      chArr[right] = temp;

	  left++;
      right--;
    }

    System.out.println(chArr);

    /*   // // How do you reverse a string in Java without using any built-in reverse functions?
    String input = "This is the city of boston";
    String input2 = "This is the city of boston";

    int len = input.length() - 1;
    StringBuilder sb = new StringBuilder();

    while (len >= 0) {
      sb.append(input.charAt(len));
      len--;
    }
    // System.out.println(sb.toString());
    // System.out.println(sb.reverse());
    Collections.reverse(Arrays.asList(input2));
    System.out.println(input2);*/

    /*String input = "This is the city of boston";
    	  List<?> arr = new ArrayList<>(List.of(input.toCharArray()));
    	  Collections.reverse(arr);
    	  System.out.println(arr.;


    */
    /*String original = "This is the city of boston";
    // Convert the string to a character array
    char[] charArray = original.toCharArray();

    // Create a list of characters
    List<Character> charList = new ArrayList<>();
    for (char c : charArray) {
     charList.add(c);
    }

    // Use Collections.reverse() to reverse the order of elements in the list
    Collections.reverse(charList);*/
  }
}
