package com.ecommerce.staples_clone.coding;

public class Question11 {

  public static void main(String[] args) {
    // Write a program to reverse a given string without using any of the built-in reverse()
    // methods.

    String myText = "Boston";
    StringBuilder reverse = new StringBuilder();

    for (int i = myText.length() - 1; i >= 0; i--) {
      reverse.append(myText.charAt(i));
    }

    System.out.println(reverse.toString());
  }
}
