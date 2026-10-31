package io.github.JorgeMor1.auth.model;

import io.github.JorgeMor1.usuarios.User;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;


@Entity
@Getter
@Setter
@Table(name = "refresh_tokens", indexes = {
        @Index(name = "idx_refresh_token_hash", columnList = "token_hash"),
        @Index(name = "idx_refresh_tokens_expires_at", columnList = "expires_at"),
        @Index(name = "idx_refresh_tokens_user_id", columnList = "user_id")
})
public class RefreshTokenEntity extends PanacheEntityBase {
   @Id
   @GeneratedValue( strategy = GenerationType.UUID )
   public UUID id;

   @Column( name = "token_hash", nullable = false, unique = true, length = 512 )
   public String tokenHash;

   @ManyToOne( fetch = FetchType.LAZY )
   @JoinColumn( name = "user_id", nullable = false )
   public User user;

   @Column( name = "expires_at", nullable = false )
   public Instant expiresAt;

   @Column( nullable = false )
   public boolean revoked = false;

   @Column(  name = "created_at", nullable = false, updatable = false )
   public Instant createdAt = Instant.now();

   @PrePersist
   public void prePersist() {
      if (this.createdAt == null) {
         this.createdAt = Instant.now();
      }
   }

   public static RefreshTokenEntity findByTokenHash( String tokenHash ) {
       return find( "tokenHash", tokenHash ).firstResult();
   }
}



