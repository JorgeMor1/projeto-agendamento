package io.github.JorgeMor1.auth;

import io.github.JorgeMor1.auth.model.RefreshTokenEntity;
import io.github.JorgeMor1.auth.repository.RefreshTokenRepository;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import io.quarkus.logging.Log;

import java.time.Instant;


@ApplicationScoped
public class RefreshTokenCleanupService {

    @Inject
    RefreshTokenRepository refreshTokenRepository;

    @Scheduled( cron = "0 0 3 * * ?" )
    @Transactional
    public void purgeExpiredOrRevokedTokens() {
        Log.info( "Iniciando a rotina de limpeza de Refresh Tokens expirados ou revogados..." );

        Instant now = Instant.now();

        // Deleta em massa do banco de dados todos os tokens que já expiraram OU já foram revogados
        long deletedCount = refreshTokenRepository.deleteExpiredOrRevokedTokens( now );

        Log.info( "Limpeza concluída com sucesso. Total de tokens removidos: " + deletedCount );
    }
}
