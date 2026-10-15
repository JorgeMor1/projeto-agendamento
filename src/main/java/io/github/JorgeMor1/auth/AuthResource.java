package io.github.JorgeMor1.auth;

import io.github.JorgeMor1.auth.dto.request.RegisterRequest;
import io.github.JorgeMor1.auth.dto.response.RegisterResponse;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
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
    public Response register(RegisterRequest request){
        RegisterResponse response = authService.register(request);

        return Response
                .status(Response.Status.CREATED)
                .entity(response)
                .build();
    }

    /*@POST
    @Path("/login")
    @PermitAll
    public LoginResponse login(LoginRequest request) {
        return authService.login(request);
    }*/
}
