package base.api.system.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
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
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    public SecurityConfig(JwtAuthenticationProvider jwtAuthenticationProvider) {
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
    }

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
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*");
            }
        };
    }

    @Bean
    SecurityFilterChain filterChain(
            HttpSecurity http,
            JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {

        http
                .cors(Customizer.withDefaults()) // FIXME allowing all origins
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
                                    .requestMatchers("/api/user/hello")
                                    .permitAll()
                                    .requestMatchers("/api/user/login")
                                    .anonymous()
                                    .requestMatchers("/api/user/register")
                                    .anonymous()
//            .requestMatchers("/api/**")
//            .authenticated()
                                    .requestMatchers("/api/test/**")
                                    .permitAll()
                                    .requestMatchers("/docs/**").access(
                                            new WebExpressionAuthorizationManager("hasIpAddress(\"127.0.0.1\") or hasIpAddress(\"::1\")"))
                                    .anyRequest()
                                    .permitAll(); // TODO authenticated()
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