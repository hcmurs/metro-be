package org.com.hcmurs.gatewayservice.security;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtAuthConverter implements Converter<Jwt, Mono<AbstractAuthenticationToken>> {

    private static final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter =
        new JwtGrantedAuthoritiesConverter();

    private final JwtAuthConverterProperties properties;

    @Override
    public Mono<AbstractAuthenticationToken> convert(Jwt jwt) {
        Set<GrantedAuthority> authorities = new HashSet<>();

        Collection<GrantedAuthority> defaultAuthorities = jwtGrantedAuthoritiesConverter.convert(
            jwt);
        if (defaultAuthorities != null) {
            authorities.addAll(defaultAuthorities);
        }

        authorities.addAll(extractResourceRoles(jwt));

        String principalClaim = properties.getPrincipalAttribute() != null ?
            properties.getPrincipalAttribute() : JwtClaimNames.SUB;
        String principal = jwt.getClaim(principalClaim);

        JwtAuthenticationToken authToken = new JwtAuthenticationToken(jwt, authorities, principal);

        return Mono.just(authToken);
    }

    private Collection<GrantedAuthority> extractResourceRoles(Jwt jwt) {
        Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
        if (resourceAccess == null) {
            return Collections.emptySet();
        }

        Object resource = resourceAccess.get(properties.getResourceId());
        if (!(resource instanceof Map)) {
            return Collections.emptySet();
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> resourceMap = (Map<String, Object>) resource;
        Object roles = resourceMap.get("roles");

        if (!(roles instanceof Collection)) {
            return Collections.emptySet();
        }

        @SuppressWarnings("unchecked")
        Collection<Object> roleCollection = (Collection<Object>) roles;

        return roleCollection.stream()
            .filter(role -> role instanceof String)
            .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
            .collect(Collectors.toSet());
    }

    private String extractUserId(Jwt jwt) {
        String userIdClaim = properties.getPrincipalAttribute() != null ?
            properties.getPrincipalAttribute() : JwtClaimNames.SUB;
        return jwt.getClaim(userIdClaim);
    }
}
