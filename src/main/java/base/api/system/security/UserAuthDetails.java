//package base.api.system.security;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.security.core.CredentialsContainer;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//import java.util.Collections;
//import java.util.Set;
//
//public class UserAuthDetails implements UserDetails, CredentialsContainer {
//
//  private static final Logger logger = LoggerFactory.getLogger(UserAuthDetails.class);
//
//  private final String userId;
//  private final String username;
//  private final Set<GrantedAuthority> authorities;
//  private final boolean accountNonExpired;
//  private final boolean accountNonLocked;
//  private final boolean credentialsNonExpired;
//  private final boolean enabled;
//  private String password;
//
//  public UserAuthDetails(
//      String userId,
//      String username,
//      String password,
//      Collection<? extends GrantedAuthority> authorities) {
//    this(userId, username, password, true, true, true, true, authorities);
//  }
//
//  public UserAuthDetails(
//      String userId,
//      String username,
//      String password,
//      boolean accountNonExpired,
//      boolean accountNonLocked,
//      boolean credentialsNonExpired,
//      boolean enabled,
//      Collection<? extends GrantedAuthority> authorities) {
//    this.userId = userId;
//    this.username = username;
//    this.password = password;
//    this.accountNonExpired = accountNonExpired;
//    this.accountNonLocked = accountNonLocked;
//    this.credentialsNonExpired = credentialsNonExpired;
//    this.enabled = enabled;
//    // FIXME: maybe need to be sorted like in org.springframework.security.core.userdetails.User
//    this.authorities = Collections.unmodifiableSet((Set<GrantedAuthority>) authorities);
//  }
//
//  @Override
//  public Collection<GrantedAuthority> getAuthorities() {
//    return this.authorities;
//  }
//
//  public String getUserId() {
//    return this.userId;
//  }
//
//  @Override
//  public String getPassword() {
//    return this.username;
//  }
//
//  @Override
//  public String getUsername() {
//    return this.username;
//  }
//
//  @Override
//  public boolean isAccountNonExpired() {
//    return this.accountNonExpired;
//  }
//
//  @Override
//  public boolean isAccountNonLocked() {
//    return this.accountNonLocked;
//  }
//
//  @Override
//  public boolean isCredentialsNonExpired() {
//    return this.credentialsNonExpired;
//  }
//
//  @Override
//  public boolean isEnabled() {
//    return this.enabled;
//  }
//
//  @Override
//  public void eraseCredentials() {
//    this.password = null;
//  }
//}
