package com.chess.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.chess.spring")
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class);
    }
}
