package ru.otus.spring.hw9;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@SpringBootApplication
public class Hw9LibraryH2Application {

	public static void main(String[] args) throws SQLException {
		SpringApplication.run(Hw9LibraryH2Application.class, args);
		Console.main(args);
	}


}
