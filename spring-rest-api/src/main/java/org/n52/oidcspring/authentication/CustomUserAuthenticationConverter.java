package org.n52.oidcspring.authentication;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CustomUserAuthenticationConverter implements UserAuthenticationConverter {

    @Override
    public Authentication extractAuthentication(Map<String, ?> map) {
        if (map.containsKey("sub")) {
            Object principal = map.get("sub");

            Collection<? extends GrantedAuthority> authorities;
            if (map.containsKey("roles") && map.get("roles") instanceof List<?>) {
                List<?> authorsList = (List<?>) map.get("roles");
                authorities = authorsList.stream().map((Object o) -> {
                    return new SimpleGrantedAuthority("ROLE_" + o.toString().toUpperCase());
                }).collect(Collectors.toList());
            } else {
                authorities = Collections.emptyList();
            }

            return new UsernamePasswordAuthenticationToken(principal, "", authorities);
        }
        return null;
    }

    @Override
    public Map<String, ?> convertUserAuthentication(Authentication userAuthentication) {
        throw new UnsupportedOperationException();
    }
}