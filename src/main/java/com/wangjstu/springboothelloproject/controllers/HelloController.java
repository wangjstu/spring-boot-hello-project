package com.wangjstu.springboothelloproject.controllers;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String hello() {
        return "Hello Spring Boot";
    }



    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext cxt) {
        return args -> {
            System.out.println("Lets have a look about bean in Spring Boot:");
            String beans[] = cxt.getBeanDefinitionNames();
            Arrays.sort(beans);
            for (String beanName: beans) {
                System.out.println(beanName);
            }
        };
    }
}
