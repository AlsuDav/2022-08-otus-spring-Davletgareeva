package ru.otus.spring.service;

import com.opencsv.exceptions.CsvException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.dao.FAQDao;
import ru.otus.spring.domain.FAQ;
import ru.otus.spring.parser.Parser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class ExamServiceImplTest {
    @InjectMocks
    private ExamServiceImpl examServiceImpl;

    @Mock
    private Parser parser;

    @Mock
    private FAQDao faqDao;

    @BeforeEach
    void setUp() {

    }

    @Test
    void startExamWithWrongType() throws URISyntaxException, IOException, CsvException {
        var faq = new FAQ();
        faq.setQuestionType("100");
        Mockito.lenient().when(parser.getFAQFromFile(FAQ.class))
                .thenReturn(List.of(faq));
        String inputName = "name \n 11 \n A \n";
        InputStream inN = new ByteArrayInputStream(inputName.getBytes());
        System.setIn(inN);

        var result = examServiceImpl.startExam(parser);

        assertEquals(false, result);
    }

    @Test
    void startExamWithRightType() throws URISyntaxException, IOException, CsvException {
        var faq1 = new FAQ();
        faq1.setQuestionType("0");
        var faq2 = new FAQ();
        faq2.setQuestionType("4");

        Mockito.lenient().when(parser.getFAQFromFile(FAQ.class))
                .thenReturn(List.of(faq1, faq2));
        Mockito.lenient().when(faqDao.checkAnswer(any(), any())).thenReturn(Boolean.TRUE);
        String inputName = "name \n 11 \n A \n A \n";
        InputStream inN = new ByteArrayInputStream(inputName.getBytes());
        System.setIn(inN);
        var result = examServiceImpl.startExam(parser);
        assertEquals(true, result);
    }
}
