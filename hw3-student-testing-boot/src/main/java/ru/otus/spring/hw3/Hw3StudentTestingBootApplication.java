package ru.otus.spring.hw3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.otus.spring.hw3.config.ServicesConfig;


@SpringBootApplication
@EnableConfigurationProperties(ServicesConfig.class)
public class Hw3StudentTestingBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(Hw3StudentTestingBootApplication.class, args);
	}
}
