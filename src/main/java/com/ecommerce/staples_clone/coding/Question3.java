package com.ecommerce.staples_clone.coding;

public class Question3 {

  public static void main(String[] args) {
    System.out.println("started");
    // Write a program to determine if a given string is a palindrome (reads the same forwards and
    // backward).  "level" and "madam".

    // 1.
    String myText = "level".toLowerCase();
    char[] convertChar = myText.toCharArray();
    boolean flag = true;

    for (int i = 0; i < (convertChar.length - 1); i++) {
      int last = (convertChar.length - 1) - i;
      if (convertChar[i] != convertChar[last]) {
        flag = false;
        break;
      }
    }
    System.out.println(flag);

    // 2.
    String myText2 = "level".toLowerCase();
    boolean flag2 = true;
    int left = 0;
    int right = (myText2.length() - 1);

    while (left < right) {
      if (myText2.charAt(left) != myText2.charAt(right)) {
        flag = false;
        break;
      }
      left++;
      right--;
    }
    System.out.println(flag);

    /*StringBuilder sb = new StringBuilder(myText);
    StringBuilder sb2 = sb.reverse();

    for(int i = 0; i< sb.length();i++){

    }


    int len = myText.length();

    if(len/2 == 0){
    	// even
    	int midLength = len/2; // boston

    	myText.split(midLength);


    } else {
    	// odd

    }*/

  }
}
