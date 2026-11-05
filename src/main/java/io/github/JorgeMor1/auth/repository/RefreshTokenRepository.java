package io.github.JorgeMor1.auth.repository;

import io.github.JorgeMor1.auth.model.RefreshTokenEntity;
import io.github.JorgeMor1.usuarios.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.Instant;
import java.util.Optional;
@ApplicationScoped
public class RefreshTokenRepository implements PanacheRepository< RefreshTokenEntity > {

    public Optional< RefreshTokenEntity > findByTokenHash( String tokenHash ) {
        return find( "tokenHash", tokenHash ).firstResultOptional();
    }

    public long deleteExpiredTokensForUser( User user ) {
        return delete( "user = ?1 and expiresAt < ?2", user, Instant.now() );
    }

    public long deleteExpiredOrRevokedTokens( Instant now ) {
        return delete( "expiresAt < ?1 or revoked = ?2", now, true );
    }
}
