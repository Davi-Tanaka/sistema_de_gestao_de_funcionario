package com.src.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.src.details.UserDetailsServiceImp;
import com.src.filtro.JwtFilter;
import com.src.services.JwtService;

@Configuration
@EnableWebSecurity
public class Seguranca {
  
  @Autowired
  private JwtService jwtService;

  @Autowired
  private UserDetailsServiceImp userDetailsServiceImp;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity hs) throws Exception {
    JwtFilter jwtFilter = new JwtFilter(jwtService, userDetailsServiceImp);

    return hs
        .csrf(e -> e.disable())
        .authorizeHttpRequests(
            e -> e
                .requestMatchers(
                    "/public/**",
                    "/auth/cadastrar",
                    "/auth/login",
                    "/"
                )
                .permitAll()
                .anyRequest()
                .authenticated()
        )
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(13);
  }
}
