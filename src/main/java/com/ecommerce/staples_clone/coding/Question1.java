package com.ecommerce.staples_clone.coding;

public class Question1 {

  public static void main(String[] args) {
    // How do you reverse a string in Java without using any built-in reverse functions?

    String myText = "This is Boston";
    char[] toChar = myText.toCharArray();
    for (int i = toChar.length - 1; i >= 0; i--) {
      System.out.print(toChar[i]);
    }

    System.out.println("");

    StringBuilder sb = new StringBuilder(myText);
    System.out.println(sb.reverse());
  }
}
