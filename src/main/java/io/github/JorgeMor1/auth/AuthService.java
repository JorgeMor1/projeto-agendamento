package io.github.JorgeMor1.auth;

import io.github.JorgeMor1.auth.dto.request.LoginRequest;
import io.github.JorgeMor1.auth.dto.request.RegisterRequest;
import io.github.JorgeMor1.auth.dto.response.LoginResponse;
import io.github.JorgeMor1.auth.dto.response.RegisterResponse;
import io.github.JorgeMor1.auth.dto.response.TokenResponse;
import io.github.JorgeMor1.auth.dto.response.UserSummaryDTO;
import io.github.JorgeMor1.auth.repository.RefreshTokenRepository;
import io.github.JorgeMor1.usuarios.User;
import io.github.JorgeMor1.usuarios.UserRepository;
import io.github.JorgeMor1.usuarios.enums.UserRole;
import io.github.JorgeMor1.usuarios.enums.UserStatus;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import io.github.JorgeMor1.auth.model.RefreshTokenEntity;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.UUID;

@ApplicationScoped
public class AuthService {

    @Inject
    UserRepository userRepository;

    @Inject
    TokenService tokenService;

    @Inject
    RefreshTokenRepository refreshTokenRepository;

    private static final Duration REFRESH_TOKEN_DURATION = Duration.ofDays(7);

    @Transactional
    public RegisterResponse register ( RegisterRequest request ) {
        if ( userRepository.findByEmail(request.email() ).isPresent() ) {
            throw new WebApplicationException( "E-mail já cadastrado no sistema", Response.Status.CONFLICT );
        }

        User user = new User();
        user.setFirstName( request.firstName() );
        user.setLastName( request.lastName() );
        user.setEmail( request.email( ) );
        user.setPasswordHash( BcryptUtil.bcryptHash(request.password() ) );
        user.setRole( UserRole.USER );
        user.setUserStatus( UserStatus.ACTIVE );

        userRepository.persist( user );

        return criarRegisterResponse( user );
    }

    @Transactional
    public LoginResponse login( LoginRequest request ) {
        User user = userRepository.findByEmail( request.email() )
                .orElseThrow( () -> new WebApplicationException( "Credenciais inválidas", Response.Status.UNAUTHORIZED ) );

        if ( !BcryptUtil.matches( request.password(), user.getPasswordHash() ) ) {
            throw new WebApplicationException( "Credenciais inválidas", Response.Status.UNAUTHORIZED );
        }

        if ( user.getUserStatus() != UserStatus.ACTIVE ) {
            throw new WebApplicationException( "Usuário inativo ou bloqueado", Response.Status.FORBIDDEN );
        }
        refreshTokenRepository.deleteExpiredTokensForUser(user);
        return criarLoginResponse( user );
    }

    @Transactional
    public TokenResponse refreshTokens( String rawRefreshToken ) {
        if ( rawRefreshToken == null || rawRefreshToken.isBlank() ) {
            throw new WebApplicationException( "Refresh token ausente", Response.Status.UNAUTHORIZED );
        }

        String hashedToken = hashToken( rawRefreshToken );
        RefreshTokenEntity existingToken = refreshTokenRepository.findByTokenHash( hashedToken )
                .orElseThrow(() -> new WebApplicationException( "Refresh token inválido, expirado ou revogado.", Response.Status.UNAUTHORIZED ) );

        if ( existingToken.isRevoked() || existingToken.getExpiresAt().isBefore( Instant.now() ) ) {
            throw new WebApplicationException( "Refresh token inválido, expirado ou revogado.", Response.Status.UNAUTHORIZED );
        }

        // Rotação: Revoga o token atual para evitar reuso
        existingToken.setRevoked( true );
        refreshTokenRepository.persist( existingToken );

        User user = existingToken.getUser();
        return gerarTokenResponse( user );
    }

    private UserSummaryDTO mapearParaSummary ( User user ) {
        return new UserSummaryDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getRole(),
                user.getUserStatus(),
                user.getCreatedAt()
        );
    }

    private TokenResponse gerarTokenResponse( User user ) {
        TokenResult tokens = tokenService.generateAccessToken( user  );
        String rawRefreshToken = criarEPersistirRefreshToken( user );
        return new TokenResponse(
                tokens.accessToken(),
                tokens.expiresAt(),
                rawRefreshToken,
                "Bearer"
        );
    }
    private RegisterResponse criarRegisterResponse( User user ) {
        return new RegisterResponse(
                mapearParaSummary( user )
        );
    }

    private LoginResponse criarLoginResponse( User user ) {
        return new LoginResponse(
                mapearParaSummary( user ),
                gerarTokenResponse( user )
        );
    }

        private String criarEPersistirRefreshToken( User user ) {
            String rawToken = UUID.randomUUID().toString() + "." + UUID.randomUUID().toString();
            String hashedToken = hashToken( rawToken );

            RefreshTokenEntity entity = new RefreshTokenEntity();
            entity.setTokenHash( hashedToken );
            entity.setUser( user );
            entity.setExpiresAt( Instant.now().plus(REFRESH_TOKEN_DURATION ) );
            entity.setRevoked( false );

            refreshTokenRepository.persist( entity );

            return rawToken;
        }

        private String hashToken( String token ) {
            try {
                MessageDigest digest = MessageDigest.getInstance( "SHA-256" );
                byte[] encodedhash = digest.digest( token.getBytes() );
                return Base64.getEncoder().encodeToString( encodedhash );
            } catch ( NoSuchAlgorithmException e ) {
                throw new RuntimeException( "Erro ao gerar hash do refresh token", e );
            }
        }

}
