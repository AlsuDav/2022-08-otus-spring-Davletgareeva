package ru.otus.spring.parser;

import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.StudentTestStarter;
import ru.otus.spring.domain.FAQ;
import ru.otus.spring.service.ExamService;

import static org.junit.jupiter.api.Assertions.*;


class OpenCsvParserTest {
    @Mock
    private OpenCsvParser parser;

    @BeforeEach
    void setUp() {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(StudentTestStarter.class);

        parser = context.getBean(OpenCsvParser.class);
    }

    @Test
    void getFileName_success() {
        assertEquals("test.csv", parser.getFileName());
    }

//    @Test
//    void getContent_success() throws URISyntaxException, IOException, CsvException {
//
//
//        var content = parser.getFAQFromFile(FAQ.class);
//        assertEquals(6, content.size());
//    }
}
