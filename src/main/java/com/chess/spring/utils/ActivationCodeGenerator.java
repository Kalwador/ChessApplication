package com.chess.spring.utils;

import java.util.Random;
import java.util.stream.IntStream;

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
        IntStream.range(0, 5).forEach(i -> code.append(chars.charAt(rnd.nextInt() * chars.length())));
        return code.toString();
    }
}