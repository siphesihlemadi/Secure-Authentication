package org.example;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

public class AuthManager {
    /**
     * Method to hash password
     * Argon2 automatically salts the password before the hashing process
     * wipes the password after hashing
     */
    public static String hashPassword(String password) {
        char[] charArrayPassword = password.toCharArray();
        Argon2 argon2 = Argon2Factory.create();
        int iterations = 3;
        int memory = 65536;
        int parallelism = 1;

        try {
            return argon2.hash(iterations, memory, parallelism, charArrayPassword);
        } finally {
            argon2.wipeArray(password.toCharArray());
        }
    }

    /**
     * method to verify a password against a stored hash
     * wipes password after use
     */
    public static boolean verifyPassword(String password, String storedHash) {
        char[] charArrayPassword = password.toCharArray();
        Argon2 argon2 = Argon2Factory.create(
                Argon2Factory.Argon2Types.ARGON2id
        );
        try {
            return argon2.verify(storedHash, charArrayPassword);
        } finally {
            argon2.wipeArray(password.toCharArray());
        }
    }
}
