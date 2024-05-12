package base.api.system.security;

import base.api.authorization.UnauthorizedException;
import org.springframework.security.core.Authentication;

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
        if (auth.getAuthorities().stream().noneMatch(authority -> authority.getAuthority().equals(role))) {
            throw new UnauthorizedException("Only admin can change payment status");
        }
    }
}
