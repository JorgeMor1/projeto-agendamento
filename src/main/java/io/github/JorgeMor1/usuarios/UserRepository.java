package io.github.JorgeMor1.usuarios;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {
    public Optional<User>  findByEmail( String email ){
        return find("email", email)
                .firstResultOptional();
    }
}
