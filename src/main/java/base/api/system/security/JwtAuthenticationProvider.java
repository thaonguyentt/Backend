package base.api.system.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {
  private JwtService jwtService;

  public JwtAuthenticationProvider(JwtService jwtService) {
    this.jwtService = jwtService;
  }

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String token = String.valueOf(authentication.getCredentials());
    // If not valid, return Authentication object with authenticated = false

    if (!jwtService.isTokenValid(token)) {
      authentication.setAuthenticated(false);
      return authentication;
    }

    String userId = jwtService.extractUserId(token);
    List<Authority> authorityList = jwtService.extractRoles(token)
      .stream()
      .map(Authority::fromName)
      .toList();
    return JwtAuthenticationToken.authenticated(userId, null, authorityList);
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(JwtAuthenticationToken.class);
  }
}
