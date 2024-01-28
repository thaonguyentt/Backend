package base.api.system.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

  protected final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
  private final JwtService jwtService;
  private final AuthenticationManager authManager;

  public JwtAuthenticationFilter(JwtService jwtService, AuthenticationManager authManager) {
    this.jwtService = jwtService;
    this.authManager = authManager;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    final String authHeader = request.getHeader("Authorization");
    final String jwtTokenString;
    final String userId;

    // No JWT in Authorization header -> continue down the chain
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    jwtTokenString = authHeader.substring(7);

    // Invalid JWT token -> continue down the chain
    if (!jwtService.isTokenValid(jwtTokenString)) {
      filterChain.doFilter(request, response);
      return;
    }

    userId = jwtService.extractUserId(jwtTokenString);

    // populate Authentication object into SecurityContext
    Authentication authenticationRequest = JwtAuthenticationToken.unauthenticated(userId, jwtTokenString);
    try {
      Authentication authenticationResult = authManager.authenticate(authenticationRequest);
      if (authenticationResult == null) { // Should not happen, per AuthenticationManager.authenticate contract
        throw new AuthenticationServiceException("Authentication object returned by AuthenticationManager.authenticate should not be null");
      }
      // Authentication success
      SecurityContextHolder.getContext().setAuthentication(authenticationResult);
    } catch (AuthenticationException authException) {
      SecurityContextHolder.clearContext();
      logger.trace("Failed to process authentication request", authException);
      logger.trace("Cleared SecurityContextHolder");
    }

    filterChain.doFilter(request, response);
  }

}
