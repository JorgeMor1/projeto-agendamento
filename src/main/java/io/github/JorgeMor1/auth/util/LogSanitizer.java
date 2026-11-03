package io.github.JorgeMor1.auth.util;

public class LogSanitizer {

    public static String maskEmail(String email) {
        if (email == null || !email.contains("@")) {
            return "***";
        }
        int index = email.indexOf("@");
        String name = email.substring(0, index);
        String maskedName = name.length() > 2 ? name.substring(0, 2) + "***" : "***";
        return maskedName + email.substring(index);
    }
}
