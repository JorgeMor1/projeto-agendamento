package io.github.JorgeMor1.auth;

import io.github.JorgeMor1.auth.dto.request.LoginRequest;
import io.github.JorgeMor1.auth.dto.request.RegisterRequest;
import io.github.JorgeMor1.auth.dto.response.LoginResponse;
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
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class AuthService {

    @Inject
    UserRepository userRepository;

    @Inject
    TokenService tokenService;

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

        return criarLoginResponse( user );
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
    };

    private TokenResponse gerarTokenResponse( User user ) {
        TokenResult tokens = tokenService.generateAccessToken( user  );
        return new TokenResponse(
                tokens.accessToken(),
                tokens.expiresAt(),
                "Bearer"
        );
    }
    private RegisterResponse criarRegisterResponse( User user ) {
        return new RegisterResponse(
                mapearParaSummary( user ),
                gerarTokenResponse( user )
        );
    }

    private LoginResponse criarLoginResponse( User user ) {
        return new LoginResponse(
                mapearParaSummary( user ),
                gerarTokenResponse( user )
        );
    }

}
