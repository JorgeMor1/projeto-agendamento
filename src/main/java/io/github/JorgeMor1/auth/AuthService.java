package io.github.JorgeMor1.auth;

import io.github.JorgeMor1.auth.dto.request.RegisterRequest;
import io.github.JorgeMor1.auth.dto.response.RegisterResponse;
import io.github.JorgeMor1.auth.dto.response.TokenResponse;
import io.github.JorgeMor1.auth.dto.response.UserSummaryDTO;
import io.github.JorgeMor1.usuarios.User;
import io.github.JorgeMor1.usuarios.UserRepository;
import io.github.JorgeMor1.usuarios.enums.UserRole;
import io.github.JorgeMor1.usuarios.enums.UserStatus;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.Instant;

@ApplicationScoped
public class AuthService {

    @Inject
    UserRepository userRepository;

    @Inject
    TokenService tokenService;


    @Transactional
    public RegisterResponse register (RegisterRequest request ) {
        if ( userRepository.findByEmail(request.email()).isPresent() ){
            try {
                throw new Throwable("Login já cadastrado");
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        User user = new User();
        user.setFirstName(request.firstName() );
        user.setLastName(request.lastName() );
        user.setEmail( request.email() );
        user.setPasswordHash( BcryptUtil.bcryptHash( request.password() ) );
        user.setRole( UserRole.USER );
        user.setUserStatus( UserStatus.ACTIVE );
        user.setCreatedAt( Instant.now() );
        user.setUpdatedAt( Instant.now() );

        userRepository.persist(user);

        TokenResult tokens = tokenService.generateAccessToken( user );

        TokenResponse tokenResponse = new TokenResponse(
                tokens.accessToken(),
                tokens.expiresAt(),
                "Bearer"
        );

        UserSummaryDTO userSummary = new UserSummaryDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getRole(),
                user.getUserStatus(),
                user.getCreatedAt()
        );

        return new RegisterResponse(
                userSummary,
                tokenResponse
        );
    }
}
