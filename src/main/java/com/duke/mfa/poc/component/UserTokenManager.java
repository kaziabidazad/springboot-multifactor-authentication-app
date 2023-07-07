package com.duke.mfa.poc.component;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

/**
 * @author Kazi
 */
@Component
public class UserTokenManager {

    private ConcurrentHashMap<String, Principal> tokenPrincipalMapping = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Integer, Set<Principal>> userIdPrincipalMapping = new ConcurrentHashMap<>();

    public Principal getUserDetails(String authToken) {
        return tokenPrincipalMapping.get(authToken);
    }

    public void addTokenToCache(Principal principal) {
        tokenPrincipalMapping.put(principal.getAuthTokenString(), principal);
        //
        Integer userId = principal.getUserId();
        if (userIdPrincipalMapping.contains(userId))
            userIdPrincipalMapping.get(userId).add(principal);
        else {
            Set<Principal> principals = new HashSet<>();
            principals.add(principal);
            userIdPrincipalMapping.put(userId, principals);
        }
    }

    public void removeTokenFromCache(String token) {
        Principal deletedPrincipal = null;
        if (tokenPrincipalMapping.contains(token)) {
            deletedPrincipal = tokenPrincipalMapping.get(token);
            tokenPrincipalMapping.remove(token);
        }
        if (deletedPrincipal != null) {
            Integer userId = deletedPrincipal.getUserId();
            Set<Principal> principals = userIdPrincipalMapping.get(userId);
            Iterator<Principal> iterator = principals.iterator();
            while (iterator.hasNext()) {
                Principal principal = iterator.next();
                if (token.equals(principal.getAuthTokenString())) {
                    iterator.remove();
                    break;
                }
            }
        }

    }

}
