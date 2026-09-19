package io.github.JorgeMor1.usuarios;

import io.github.JorgeMor1.usuarios.dto.RegisterDTO;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    @Inject
    UserRepository userRepository;

    @POST
    @Path("/register")
    @Transactional
    public Response createUser(@Valid RegisterDTO dto){
        User user = new User();
        user.setNome(dto.getNome());
        user.setSobrenome(dto.getSobrenome());
        user.setEmail(dto.getEmail());
        user.setSenha(dto.getSenha());
        user.setRole(dto.getRole());
        userRepository.persist(user);
        return Response.status(Response.Status.CREATED).build();

    }
}
