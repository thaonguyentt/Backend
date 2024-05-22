package base.api.system.security;

import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

public final class Identity {
    @Getter
    private final Long userId;
    @Getter
    private final Set<String> roles;

    private boolean isAnonymous = false;
    private boolean isAuthenticated = false;

    public static final Identity SYSTEM = unsafeAuthenticated(0L, Set.of("SYSTEM"));

    // Disallow object creation from outside package
    private Identity() {
        throw new UnsupportedOperationException("No-args constructor not allowed");
    }
    private Identity(Long userId, Collection<String> roles) {
        this.userId = userId;
        this.roles = roles.stream().collect(Collectors.toUnmodifiableSet());
    }

    public boolean hasAllRoles(String... roles) {
        if (roles == null || roles.length == 0) { return false; }
        if (this.roles == null || this.roles.isEmpty()) { return false; }

        return Arrays.stream(roles).allMatch(this.roles::contains);
    }

    public boolean hasAnyRole(String... roles) {
        if (roles == null || roles.length == 0) { return false; }
        if (this.roles == null || this.roles.isEmpty()) { return false; }

        return Arrays.stream(roles).anyMatch(this.roles::contains);
    }

    public boolean isAuthenticated() {
        return this.isAuthenticated;
    }

    public boolean isAnonymous() { return this.isAnonymous; }

    public static Identity unsafeAuthenticated(Long userId, Collection<String> roles) {
        if (userId == null) { throw new IllegalArgumentException("userId cannot be null"); }
        if (roles == null) { roles = Collections.emptySet(); }
        Identity identity = new Identity(userId, roles);
        identity.isAuthenticated = true;
        return identity;
    }

    public static Identity unauthenticated(Long userId, Collection<String> roles) {
        if (userId == null) { throw new IllegalArgumentException("userId cannot be null"); }
        if (roles == null) { roles = Collections.emptySet(); }
        return new Identity(userId, roles);
    }

    public static Identity anonymous() {
        Identity identity = new Identity(null, Collections.emptySet());
        identity.isAnonymous = true;
        return identity;
    }
}
