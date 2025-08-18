package org.example;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

public class AuthManager {
    /**
     * Method to hash password
     */
    public static String hashPassword(String password) {
        Argon2 argon2 = Argon2Factory.create(
                Argon2Factory.Argon2Types.ARGON2id
        );
        int iterations = 3;
        int memory = 65536;
        int parallelism = 1;

        try{
            return argon2.hash(iterations,memory,parallelism,password);
        }finally {
            argon2.wipeArray(password.toCharArray());
        }
    }
    /**
     * method to verify a password against a stored hash
     */
    public static boolean verifyPassword(String password, String storedHash){
        Argon2 argon2 = Argon2Factory.create(
                Argon2Factory.Argon2Types.ARGON2id
        );
        try{
            return argon2.verify(storedHash,password);
        }finally {
            argon2.wipeArray(password.toCharArray());
        }
    }
}
