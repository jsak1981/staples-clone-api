package com.ecommerce.staples_clone.service;

import com.ecommerce.staples_clone.util.AppUtils;
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
      return new User(
          "user",
          AppUtils.getEncodedPassword("password"),
          Collections.singletonList((new SimpleGrantedAuthority(("ROLE_USER")))));
    } else if ("admin".equals(username)) {
      return new User(
          "admin",
          AppUtils.getEncodedPassword("adminpass"),
          Collections.singletonList((new SimpleGrantedAuthority(("ROLE_ADMIN")))));
    } else {
      throw new UsernameNotFoundException("User not found with username: " + username);
    }
  }
}
