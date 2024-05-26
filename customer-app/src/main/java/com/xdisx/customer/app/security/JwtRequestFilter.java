package com.xdisx.customer.app.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

  private final byte[] secret;

  public JwtRequestFilter(@Value("${jwt.secret}") String secret) {
    this.secret = Base64.getDecoder().decode(secret);
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {

    final String authorizationHeader = request.getHeader("Authorization");
    final String internalHeader = request.getHeader("X-Internal-Request");

    if ("customer-service".equals(internalHeader)) {
      chain.doFilter(request, response);
      return;
    }

    String username = null;
    String jwt = null;

    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      jwt = authorizationHeader.substring(7);
      try {
        Claims claims =
            Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(jwt).getBody();
        username = claims.getSubject();
      } catch (Exception e) {
        logger.error("JWT token validation failed", e);
      }
    }

    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      CustomAuthenticationToken authenticationToken =
          new CustomAuthenticationToken(username, null, null);
      authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    chain.doFilter(request, response);
  }
}
