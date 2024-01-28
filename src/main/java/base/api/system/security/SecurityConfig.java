package base.api.system.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Autowired
  private JwtAuthenticationProvider jwtAuthenticationProvider;

  @Bean
  public JwtAuthenticationFilter jwtAuthenticationFilter(JwtService jwtService, AuthenticationManager authManager) {
    return new JwtAuthenticationFilter(jwtService, authManager);
  }

  @Bean
  public AuthenticationManager authManager(HttpSecurity http) throws Exception {
    AuthenticationManagerBuilder authenticationManagerBuilder =
      http.getSharedObject(AuthenticationManagerBuilder.class);
    authenticationManagerBuilder.authenticationProvider(jwtAuthenticationProvider);
    return authenticationManagerBuilder.build();
  }

  @Bean
  SecurityFilterChain filterChain(
      HttpSecurity http,
      JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {

    http
      .httpBasic(httpBasic -> httpBasic.disable())
      .formLogin(httpFormLogin -> httpFormLogin.disable())

      .csrf(csrf -> csrf.disable())
      .sessionManagement(
        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .authorizeHttpRequests(
        auth -> {
          auth
            // Authorize this endpoint to enable error response of others endpoint
            // Ref: https://docs.spring.io/spring-boot/docs/3.1.x/reference/htmlsingle/#web.servlet.spring-mvc.error-handling
            .requestMatchers("/error").permitAll()
            .requestMatchers("/docs/**").access(
              new WebExpressionAuthorizationManager("hasIpAddress(\"127.0.0.1\") or hasIpAddress(\"::1\")"))
            .requestMatchers("/api/user/login")
            .anonymous()
            .requestMatchers("/api/user/register")
            .anonymous()
            .requestMatchers("/api/user/hello")
            .authenticated()
            .requestMatchers("/api/test/**").permitAll()
            .anyRequest()
            .authenticated();
        })
      .addFilterBefore(jwtAuthenticationFilter, BasicAuthenticationFilter.class)
      .exceptionHandling(exceptionHandling
        -> exceptionHandling.defaultAuthenticationEntryPointFor(
          new Http403ForbiddenEntryPoint(), new AntPathRequestMatcher("/api/**")
      ))
    ;
    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
