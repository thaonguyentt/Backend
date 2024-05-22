package base.api.system.security;

import base.api.authorization.UnauthorizedException;
import org.springframework.security.core.Authentication;

import java.util.Arrays;

@Deprecated
public class SecurityUtils {
    private SecurityUtils() {}

    public static void requireAuthentication(Authentication auth) {
        if (auth == null || auth.getPrincipal() == null) {
            throw new UnauthorizedException();
        }
    }

    public static void requireHasRole(Authentication auth, String role) {
        if (role == null || role.isBlank()) {
            throw new IllegalArgumentException("role string must not be blank");
        }
        if (!auth.isAuthenticated()) {
            throw new UnauthorizedException("Must be authenticated");
        }
        if (auth.getAuthorities().stream().noneMatch(authority -> authority.getAuthority().equals(role))) {
            throw new UnauthorizedException("No role matched");
        }
    }

    public static void requireHasRoleAny(Authentication auth, String... roles) {
        if (roles == null) {
            throw new IllegalArgumentException("role string must not be blank");
        }
        if (!auth.isAuthenticated()) {
            throw new UnauthorizedException("Must be authenticated");
        }
        if (auth.getAuthorities().stream().noneMatch(authority -> Arrays.asList(roles).contains(authority.getAuthority()))) {
            throw new UnauthorizedException("No role matched");
        }
    }
}
