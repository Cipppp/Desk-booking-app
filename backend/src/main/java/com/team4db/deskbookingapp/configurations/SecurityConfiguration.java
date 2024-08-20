package com.team4db.deskbookingapp.configurations;

import com.team4db.deskbookingapp.jwt.filters.JWTRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
  private final JWTRequestFilter jwtRequestFilter;

  public SecurityConfiguration(JWTRequestFilter jwtRequestFilter) {
    this.jwtRequestFilter = jwtRequestFilter;
  }

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    /*
     *
     * BPS = bypass security
     *
     */
    httpSecurity
        .csrf()
        .disable()
        .authorizeRequests()
        .antMatchers(
            "/user/**",
            "/data/user",
            "/bps/**",
            "/email",
            "/user/logout",
            "/swagger-ui.html",
            "localhost:3000/signup",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/desk/**",
            "/booking/**",
            "/h2-console/**")
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .exceptionHandling()
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    httpSecurity.headers().frameOptions().disable();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }
}
