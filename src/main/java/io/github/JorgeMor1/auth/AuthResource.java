package io.github.JorgeMor1.auth;

import io.github.JorgeMor1.auth.dto.request.LoginRequest;
import io.github.JorgeMor1.auth.dto.request.RefreshTokenRequest;
import io.github.JorgeMor1.auth.dto.request.RegisterRequest;
import io.github.JorgeMor1.auth.dto.response.LoginResponse;
import io.github.JorgeMor1.auth.dto.response.RegisterResponse;
import io.github.JorgeMor1.auth.dto.response.TokenResponse;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    AuthService authService;

    @POST
    @Path("/register")
    @PermitAll
    public Response register( @Valid RegisterRequest request ){
        RegisterResponse response = authService.register( request );

        return Response
                .status( Response.Status.CREATED )
                .entity( response )
                .build();
    }

    @POST
    @Path("/login")
    @PermitAll
    public Response login( @Valid LoginRequest request ) {
        LoginResponse response = authService.login( request );
        return Response.ok( response ).build();
    }

    @POST
    @Path("/refresh")
    public Response refresh( @Valid RefreshTokenRequest request ) {
        TokenResponse tokens = authService.refreshTokens( request.refreshToken() );
        return Response.ok( tokens ).build();
    }
}
