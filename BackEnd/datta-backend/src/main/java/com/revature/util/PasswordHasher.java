package com.revature.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHasher {
    //determines how strong the algorithm is
    // 10-12 is said to be a good balance between performance and security
    private static final int WORKLOAD = 12;

    public static String hashPassword(String plainPassword) {
        String salt = BCrypt.gensalt(WORKLOAD);
        return BCrypt.hashpw(plainPassword, salt);
    }

    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}