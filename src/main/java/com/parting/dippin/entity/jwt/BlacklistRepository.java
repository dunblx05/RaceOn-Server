package com.parting.dippin.entity.jwt;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BlacklistRepository {

    private final RedisTemplate<String, String> blacklistRedisTemplate;
    private static final String BLACKLIST_PREFIX = "BLACKLIST:";

    public void addToBlacklist(String token, Duration expirationTime) {
        blacklistRedisTemplate.opsForValue().set(BLACKLIST_PREFIX + token, "BLACKLISTED", expirationTime);
    }

    public boolean isBlacklisted(String token) {
        return blacklistRedisTemplate.hasKey(BLACKLIST_PREFIX + token);
    }
}
