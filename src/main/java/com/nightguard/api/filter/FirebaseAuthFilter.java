package com.nightguard.api.filter;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class FirebaseAuthFilter extends OncePerRequestFilter {

  private final FirebaseAuth firebaseAuth;

  public FirebaseAuthFilter(FirebaseAuth firebaseAuth) {
    this.firebaseAuth = firebaseAuth;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain)
      throws ServletException, IOException {

    String authHeader = request.getHeader("Authorization");

    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      String token = authHeader.substring(7);
      try {
        FirebaseToken decoded = firebaseAuth.verifyIdToken(token);
        // Put the token into the security context
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
            decoded.getUid(), null, List.of());
        authentication.setDetails(decoded); // access email, claims, etc.
        SecurityContextHolder.getContext().setAuthentication(authentication);
      } catch (FirebaseAuthException e) {
        SecurityContextHolder.clearContext();
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Firebase token");
        return;
      }
    }

    filterChain.doFilter(request, response);
  }
}