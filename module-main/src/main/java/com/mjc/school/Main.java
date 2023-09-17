package com.mjc.school;

import com.mjc.school.view.View;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext("com.mjc.school");
        View consoleView = context.getBean("consoleView", View.class);
        consoleView.show();
    }
}

