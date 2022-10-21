package ru.otus.spring.hw1;

import com.opencsv.exceptions.CsvException;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.hw1.parser.OpenCsvParser;

import java.io.IOException;
import java.net.URISyntaxException;


public class Main {

    public static void main(String[] args) throws URISyntaxException, IOException, CsvException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        var parser = context.getBean(OpenCsvParser.class);
        var content = parser.getContentFromFile();
        parser.printContent(content);


    }

}



