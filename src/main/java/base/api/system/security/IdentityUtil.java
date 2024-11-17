package base.api.system.security;

import base.api.common.exception.AuthenticationException;
import base.api.common.exception.AuthorizationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public final class IdentityUtil {

    private IdentityUtil() {}

    public static Identity fromSpringAuthentication(Authentication authentication) {
        if (authentication == null) return null;
        if ("anonymousUser".equals(authentication.getPrincipal())) {
            return Identity.anonymous();
        }

        Long userId = Long.valueOf((String)authentication.getPrincipal());
        Set<String> roles =
          authentication.getAuthorities()
            .stream().map(GrantedAuthority::getAuthority)
            .collect(Collectors.toSet());

        if (authentication.isAuthenticated()) {
            // FIXME xoá workaround tại đây, lưu role user trong db
            if (roles.isEmpty() || (roles.size() == 1 && roles.contains(""))) {
                roles.add("USER");
            }
            return Identity.unsafeAuthenticated(userId, roles);
        }
        return Identity.unauthenticated(userId, roles);
    }

    public static Identity getIdentity() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return fromSpringAuthentication(authentication);
    }


    public static void requireAuthenticated(Identity identity) {
        if (identity == null) {
            throw new IllegalArgumentException("Identity required");
        }
        if (!identity.isAuthenticated()) {
            throw new AuthenticationException("User is not authenticated");
        }
        if (identity.getUserId() == null) {
           throw new IllegalArgumentException("Identity without user id");
        }
    }
    public static void requireHasAnyRole(Identity identity, String... roles) {
        if (identity == null) throw new AuthorizationException("Authorization failed: no Identity provided");
        if (roles == null || roles.length == 0) return;
        if (Arrays.stream(roles).noneMatch(identity.getRoles()::contains)) {
            throw new AuthorizationException("Authorization failed: operation not permitted");
        }
    }
}
