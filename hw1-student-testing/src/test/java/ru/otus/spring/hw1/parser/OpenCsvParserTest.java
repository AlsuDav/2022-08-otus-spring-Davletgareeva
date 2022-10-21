package ru.otus.spring.hw1.parser;

import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.jupiter.api.*;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.jupiter.api.Assertions.*;


class OpenCsvParserTest {
    private OpenCsvParser parser;

    @BeforeEach
    void setUp() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        parser = context.getBean(OpenCsvParser.class);
    }

    @Test
    void getFileName_success() {
        assertEquals("test.csv", parser.getFileName());
    }

    @Test
    void getContent_success() throws URISyntaxException, IOException, CsvException {
        var content = parser.getContentFromFile();
        assertEquals(5, content.size());
    }


}
