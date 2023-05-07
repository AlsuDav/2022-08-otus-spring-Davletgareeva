package ru.otus.spring.hw11;


import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@SpringBootApplication
public class Hw11LibraryOrmJpaApplication {

    public static void main(String[] args) throws SQLException {
        SpringApplication.run(Hw11LibraryOrmJpaApplication.class, args);
        Console.main(args);
    }


}
