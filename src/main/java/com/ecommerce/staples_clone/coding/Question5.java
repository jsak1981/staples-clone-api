package com.ecommerce.staples_clone.coding;

import java.util.Arrays;

public class Question5 {

  public static void main(String[] args) {
    System.out.println("start!!");

    // a word, phrase, or name formed by rearranging the letters of another, such as cinema, formed
    // from iceman.
    // Write a function to check if two strings are anagrams of each other.

    String str1 = "ci nema";
    String str2 = "icema n";
    boolean flag = true;


    String newStr1 = str1.replaceAll("\\s","").toLowerCase();
    String newStr2 = str2.replaceAll("\\s","").toLowerCase();

    if(newStr1.length() != newStr2.length()){
      flag = false;
    }

    char[] ch1 = newStr1.toCharArray();
    char[] ch2 = newStr2.toCharArray();

    Arrays.sort(ch1);
    Arrays.sort(ch2);

    System.out.println(ch1);
    System.out.println(ch2);

    if(!Arrays.equals(ch1,ch2)){
      flag = false;
    }


    /*if (str1.length() == str2.length()) {
      for (char ch : str1.toCharArray()) {
        if (!str2.contains("" + ch)) {
          flag = false;
          break;
        }
      }
    } else {
      flag = false;
    }*/

    System.out.println(flag);
  }
}
