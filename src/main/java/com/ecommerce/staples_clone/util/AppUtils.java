package com.ecommerce.staples_clone.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public final class AppUtils {

  private AppUtils() {
    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }

  public static String getEncodedPassword(String rawPassword) {

    BCryptPasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();
    return (rawPassword == null) ? null : PASSWORD_ENCODER.encode(rawPassword);
  }

  public static boolean isNullOrBlank(String str) {
    return str == null || str.trim().isEmpty();
  }
}
