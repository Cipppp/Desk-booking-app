package com.team4db.deskbookingapp.jwt.filters;

import com.team4db.deskbookingapp.jwt.advice.UserControllerAdvice;
import com.team4db.deskbookingapp.jwt.exception.JWTException;
import com.team4db.deskbookingapp.jwt.model.ErrorMessage;
import com.team4db.deskbookingapp.jwt.service.UserDetailsServiceImpl;
import com.team4db.deskbookingapp.jwt.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Slf4j

@Component
@RequiredArgsConstructor
public class JWTRequestFilter extends OncePerRequestFilter {
  private final UserDetailsServiceImpl userDetailsService;
  private final JWTUtil jwtUtil;

  private final UserControllerAdvice userControllerAdvice;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {
    final String authorizationHeader = request.getHeader("Authorization");
    String username = null;
    String jwt = null;

    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      jwt = authorizationHeader.substring(7);
      username = jwtUtil.extractEmail(jwt);
    }

    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
      try {
        if (jwtUtil.validateToken(jwt, userDetails)) {
          UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
              new UsernamePasswordAuthenticationToken(
                  userDetails, null, userDetails.getAuthorities());
          usernamePasswordAuthenticationToken.setDetails(
              new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
      } catch (JWTException e) {
        log.error("jwt request filter error message");
        ErrorMessage errorMessage = userControllerAdvice.handleJWTExceptionFromFilter(e);
        response.setStatus(errorMessage.getStatusCode());
        response.getWriter().write(errorMessage.getMessage());
        return;
      }
    }
    chain.doFilter(request, response);
  }
}
