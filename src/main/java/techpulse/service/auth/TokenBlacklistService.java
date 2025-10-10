package techpulse.service.auth;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenBlacklistService {

    private final Map<String, Long> blacklist = new ConcurrentHashMap<>();

    public void blacklistToken(String token, long expirationTime) {
        blacklist.put(token, expirationTime);
    }

    public boolean isTokenBlacklisted(String token) {
        Long expiry = blacklist.get(token);
        if (expiry == null) return false;

        if (System.currentTimeMillis() > expiry) {
            blacklist.remove(token);
            return false;
        }
        return true;

    }


}
