package io.github.JorgeMor1.auth;

import jakarta.enterprise.context.ApplicationScoped;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.time.Duration;

@ApplicationScoped
public class LoginAttemptService {

    private final Cache< String, Integer > failureCache = Caffeine.newBuilder()
            .maximumSize( 10000 )
            .expireAfterWrite( Duration.ofMinutes( 15 ) )
            .build();

    private static final int MAX_ATTEMPTS = 5;

    public boolean isBlocked( String email ) {
        Integer attempts = failureCache.getIfPresent( email.toLowerCase() );
        return attempts != null && attempts >= MAX_ATTEMPTS;
    }

    public void addFailure( String email ) {
        String key = email.toLowerCase();
        Integer attempts = failureCache.getIfPresent( key );
        if ( attempts == null ) {
            failureCache.put(key, 1);
        } else {
            failureCache.put( key, attempts + 1 );
        }
    }

    public void resetAttempts( String email ) {
        failureCache.invalidate( email.toLowerCase() );
    }
}
