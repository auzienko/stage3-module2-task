package com.mjc.school.view.console.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Scanner;

@Configuration
public class ConsoleViewConfig {

    @Bean
    Scanner scanner() {
        return new Scanner(System.in);
    }
}
