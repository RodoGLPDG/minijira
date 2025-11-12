package com.portafolio.minijira.config.BCrypt;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtils {

    // Encripta la contraseña
    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    // Compara una contraseña en texto plano con el hash guardado
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
