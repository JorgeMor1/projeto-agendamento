package io.github.JorgeMor1.auth.logging;

import io.github.JorgeMor1.auth.util.LogSanitizer;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SecureAuthLoggingService {
    public void logSuccessfulLogin( String userId, String clientIp ) {
        Log.infof( "Login bem-sucedido | userId: %s", userId );
    }

    public void logFailedLoginAttempt( String usernameOrEmail, String clientIp ) {
        String maskedUser = LogSanitizer.maskEmail( usernameOrEmail );
        Log.warnf( "Falha na autenticação | usuário: %s", maskedUser );
    }

    public void logTokenReuseDetected( String userId, String clientIp ) {
        Log.errorf( "ALERTA DE SEGURANÇA: Tentativa de reutilização de token revogado! userId: %s", userId );
    }
}
