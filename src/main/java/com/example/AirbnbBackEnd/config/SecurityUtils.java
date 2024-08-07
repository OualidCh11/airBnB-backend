package com.example.AirbnbBackEnd.config;

import com.example.AirbnbBackEnd.entity.Authority;
import com.example.AirbnbBackEnd.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SecurityUtils {
    public static final String ROLE_TENANT = "ROLE_TENANT";
    public static final String ROLE_LANDLORD = "ROLE_LANDLORD";
    public static final String CLAIMS_NAMESPACE = "https://www.codecake.fr/roles";

    public static User mapOauth2AttributesToUser(Map<String,Object> attribuate){

        User user = new User();
        String sub = String.valueOf(attribuate.get("sub"));

        String username = null;

        if(attribuate.get("prefered_name") != null){
            username = ((String) attribuate.get("prefered_name")).toLowerCase();
        }

        if (attribuate.get("given_name")!= null){
            user.setFirstName(((String) attribuate.get("given_name")));
        } else if ((attribuate.get("nickname")!= null)) {
            user.setFirstName(((String) attribuate.get("nickname")));
        }

        if (attribuate.get("family_name")!= null){
            user.setLastName(((String) attribuate.get("family_name")));
        }

        if (attribuate.get("email")!= null){
            user.setEmail(((String) attribuate.get("email")));
        } else if (sub.contains("|")&&(username != null && username.contains("@"))) {
            user.setEmail(username);
        }else {
            user.setEmail(sub);
        }

        if (attribuate.get("picture")!= null){
            user.setImageUrl(((String) attribuate.get("picture")));
        }

        if(attribuate.get(CLAIMS_NAMESPACE) != null) {
            List<String> authoritiesRaw = (List<String>) attribuate.get(CLAIMS_NAMESPACE);
            Set<Authority> authorities = authoritiesRaw.stream()
                    .map(authority -> {
                        Authority auth = new Authority();
                        auth.setName(authority);
                        return auth;
                    }).collect(Collectors.toSet());
            user.setAuthorities(authorities);
        }
        return user;
    }

    public static List<SimpleGrantedAuthority> extractAuthorityFromClaims(Map<String,Object> claims){
        return mapRolesToGrantedAuthorities(getRolesFromClaims(claims));
    }

    public static Collection<String> getRolesFromClaims(Map<String,Object> claims){
        return (List<String>) claims.get(CLAIMS_NAMESPACE);
    }

    public static List<SimpleGrantedAuthority> mapRolesToGrantedAuthorities(Collection<String> roles){
        return roles.stream().filter(role -> role.startsWith("Role_")).map(SimpleGrantedAuthority::new).toList();
    }

    public static boolean hasCurrentUserAnyOfAuthorities (String ...authorities){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (authentication != null && getAuthorities(authentication)
                .anyMatch(authority -> Arrays
                        .asList(authorities).contains(authority)));
    }

    public static Stream<String> getAuthorities(Authentication authentication){

        Collection<? extends GrantedAuthority> authorities = authentication instanceof JwtAuthenticationToken jwtAuthenticationToken ?
                extractAuthorityFromClaims(jwtAuthenticationToken.getToken().getClaims()):authentication.getAuthorities();
        return authorities.stream().map(GrantedAuthority::getAuthority);
    }
}

