package com.chess.spring.profile;

import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 * Class which is using to cypher passwords and equals them
 */
public class BCryptEncoder {
    private static final BCrypt bcrypt = new BCrypt();

    /**
     * Method to cypher PASSWORD by BCrypt algorithm
     *
     * @param password  this is parameter where is stored PASSWORD
     * @return method return encrypted PASSWORD as as String
     */
    public static String encode(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    /**
     * This method is use to equals encrypted passwords
     *
     * @param setPassword     this is PASSWORD which user give
     * @param correctPassword this is PASSWORD which is original user PASSWORD
     * @return method return true if passwords are the same
     */
    public static boolean check(String setPassword, String correctPassword) {
        return BCrypt.checkpw(setPassword, correctPassword);
    }
}