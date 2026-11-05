package io.github.JorgeMor1.auth;

import io.github.JorgeMor1.usuarios.User;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.jwt.Claims;

import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@ApplicationScoped
public class TokenService {
    private static final String ISSUER = "api-agendamento";
    private static final String AUDIENCE = "payment-api";
    private static final Duration ACCESS_TOKEN_DURATION = Duration.ofMinutes(15);

    public TokenResult  generateAccessToken( User user ) {

        Instant now = Instant.now();
        Instant expiresAt = now.plus( ACCESS_TOKEN_DURATION );

        if ( user.getRole() == null ){
            throw new IllegalStateException( "Não é possível gerar um token para um usuário sem papel." );
        }

        Set< String > roles = new HashSet<>();
        roles.add( user.getRole().getName() );

        String accessToken = Jwt
                .issuer( ISSUER )
                .audience( AUDIENCE )
                .subject( user.getEmail() )
                .groups( roles )
                .claim( "userId", user.getId().toString() )
                .claim( Claims.jti.name(), UUID.randomUUID().toString() )
                .issuedAt( now )
                .expiresAt( expiresAt )
                .sign();
        return new TokenResult( accessToken, expiresAt );
    }
}
