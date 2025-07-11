package org.bram.configuration;

import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class TokenBlacklist {

    private final Set<String> blacklistedTokens = ConcurrentHashMap.newKeySet();

    public void blackListToken(String token) {
        blacklistedTokens.add(token);
    }

    public boolean isTokenBlacklisted(String token) {
        return blacklistedTokens.contains(token);
    }

}
