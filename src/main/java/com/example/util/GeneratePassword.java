package com.example.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @description: TODO
 * @author: Falcone
 * @date: 2021/9/26 14:08
 */
public class GeneratePassword {
    public static String generatePassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String pwd1 = "$2a$10$9SIFu8l8asZUKxtwqrJM5ujhWarz/PMnTX44wXNsBHfpJMakWw3M6";
        if(encoder.matches("pwd", pwd1)) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }
    }
}
