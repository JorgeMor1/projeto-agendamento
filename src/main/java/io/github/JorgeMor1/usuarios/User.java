package io.github.JorgeMor1.usuarios;

import io.github.JorgeMor1.usuarios.enums.UserRole;
import io.github.JorgeMor1.usuarios.enums.UserStatus;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.util.UUID;


@Getter
@Entity
@Table(name = "users")
public class User extends PanacheEntityBase {
    @Id
    @GeneratedValue
    @UuidGenerator
    public UUID id;

    @Column(name = "first_name", nullable = false, length = 150)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 150)
    private String lastName;

    @Column(name = "email", unique = true, nullable = false, length = 250)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 30)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private UserStatus userStatus;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @PrePersist
    void onCreate(){
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
        if (this.userStatus == null) this.userStatus = UserStatus.ACTIVE;
        if(this.role == null) this.role = UserRole.USER;
    }

    @PreUpdate
    void onUpdate() {
        this.updatedAt = Instant.now();
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
