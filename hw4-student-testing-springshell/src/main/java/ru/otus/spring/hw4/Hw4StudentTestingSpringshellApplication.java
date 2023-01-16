package ru.otus.spring.hw4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.otus.spring.hw4.config.ServicesConfig;


@SpringBootApplication
@EnableConfigurationProperties(ServicesConfig.class)
public class Hw4StudentTestingSpringshellApplication {

    public static void main(String[] args) {
        SpringApplication.run(Hw4StudentTestingSpringshellApplication.class, args);
    }
}


