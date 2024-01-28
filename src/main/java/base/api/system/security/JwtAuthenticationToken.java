package base.api.system.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import java.util.Collection;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {
  private final Object principal;

  private Object jwtToken;


  public JwtAuthenticationToken(Object principal, Object credentials) {
    super(null);
    this.principal = principal;
    this.jwtToken = credentials;
    setAuthenticated(false);
  }

  /**
   * Creates a token with the supplied array of authorities.
   *
   * @param authorities the collection of <tt>GrantedAuthority</tt>s for the principal represented
   *     by this authentication object.
   * @param principal
   * @param credentials
   */
  public JwtAuthenticationToken(Object principal, Object credentials,
      Collection<? extends GrantedAuthority> authorities) {
    super(authorities);
    this.principal = principal;
    this.jwtToken = credentials;
    // Khi Authentication được khởi tạo tại filter thì authenticated luôn = false
    // sau đó khi verify tại AuthenticationProvider (load authority list vào context)
    // thì mới set authenticated = true
    super.setAuthenticated(true);
  }

  public static JwtAuthenticationToken unauthenticated(Object principal, Object credentials) {
    return new JwtAuthenticationToken(principal, credentials);
  }

  public static JwtAuthenticationToken authenticated(Object principal, Object credentials,
      Collection<? extends GrantedAuthority> authorities) {
    return new JwtAuthenticationToken(principal, credentials, authorities);
  }

  @Override
  public Object getCredentials() {
    return this.jwtToken;
  }

  @Override
  public Object getPrincipal() {
    return this.principal;
  }

  @Override
  public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
    Assert.isTrue(!isAuthenticated,
      "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
    super.setAuthenticated(false);
  }

  @Override
  public void eraseCredentials() {
    super.eraseCredentials();
    this.jwtToken = null;
  }
}
