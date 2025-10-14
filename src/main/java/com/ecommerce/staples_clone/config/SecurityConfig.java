package com.ecommerce.staples_clone.config;

import com.ecommerce.staples_clone.jwt.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private final JwtRequestFilter jwtRequestFilter;
  private final UserDetailsService userDetailsService;

  @Autowired
  public SecurityConfig(JwtRequestFilter j, UserDetailsService u) {
    this.jwtRequestFilter = j;
    this.userDetailsService = u;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConig)
      throws Exception {
    return authConig.getAuthenticationManager();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf()
        .disable()
        .authorizeRequests()

        // permit for all endpoints for auth.
        .antMatchers("/api/auth/**")
        .permitAll()

        // permit for all endpoints for orders.
        .antMatchers("/api/orders/**")
        .permitAll()

        // permit for all endpoints for products.
        .antMatchers("/api/products/**")
        .permitAll()

        // permit for all endpoints for customers.
        .antMatchers("/api/customers/**")
        .permitAll()

        // 1. Only allow users with ROLE_USER to access /hello
        .antMatchers("/api/hello")
        .hasRole("USER")

        // 2. Only allow users with ROLE_ADMIN to access /admin/**
        .antMatchers("/api/admin/**")
        .hasRole("ADMIN")

        // 3. All other requests must be authenticated
        .anyRequest()
        .authenticated()
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  /*@Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf()
        .disable()
        // Define authorization rules
        .authorizeRequests()

        // 1. Only allow users with ROLE_USER to access /hello
        .antMatchers("/hello")
        .hasRole("USER")

        // 2. Only allow users with ROLE_ADMIN to access /admin/**
        .antMatchers("/admin/**")
        .hasRole("ADMIN")

        // All other requests must be authenticated
        .anyRequest()
        .authenticated()
        .and()
        // Set the session management to STATELESS
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        // Add your custom JWT filter
        .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }*/
}
