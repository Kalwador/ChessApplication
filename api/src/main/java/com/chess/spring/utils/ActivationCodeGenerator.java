package com.chess.spring.utils;

import java.util.Random;

/**
 * Class contains method to generate short random code with upper and lower case and numbers
 */
public class ActivationCodeGenerator {
    /**
     * This method perform generating unique code that required to unlock account
     */
    public static String generateCode() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        StringBuilder code = new StringBuilder();
        Random rnd = new Random();
        while (code.length() < 5) { // length of the random string.
            int index = (int) (rnd.nextFloat() * chars.length());
            code.append(chars.charAt(index));
        }
        return code.toString();
    }
}