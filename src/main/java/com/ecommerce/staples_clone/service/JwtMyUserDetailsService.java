package com.ecommerce.staples_clone.service;

import com.ecommerce.staples_clone.util.PasswordEncoderUtil;
import java.util.Collections;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtMyUserDetailsService implements UserDetailsService {

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    if ("user".equals(username)) {
      final String encodedPassword = PasswordEncoderUtil.getEncodedPassword("password");
      return new User(
          "user",
          encodedPassword,
          Collections.singletonList((new SimpleGrantedAuthority(("ROLE_USER")))));
    } else if ("admin".equals(username)) {
      final String encodedPassword = PasswordEncoderUtil.getEncodedPassword("adminpass");
      return new User(
          "admin",
          encodedPassword,
          Collections.singletonList((new SimpleGrantedAuthority(("ROLE_ADMIN")))));
    } else {
      throw new UsernameNotFoundException("User not found with username: " + username);
    }
  }
}
