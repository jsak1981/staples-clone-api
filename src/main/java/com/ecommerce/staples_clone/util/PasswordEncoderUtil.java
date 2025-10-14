package com.ecommerce.staples_clone.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderUtil {

  /*public static void main(String[] args) {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    String rawPassword = "password";
    // String rawPassword = "password"; // regular user
    // String rawPassword = "adminpass"; // admin user
    String encodedPassword = encoder.encode(rawPassword);

    System.out.println(encodedPassword);
  }*/

  public static String getEncodedPassword(String rawPassword) {
    // String rawPassword = "password"; // regular user
    // String rawPassword = "adminpass"; // admin user
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    String encodedPassword = encoder.encode(rawPassword);

    System.out.println("Encoded password :" + encodedPassword);
    return encodedPassword;
  }
}
