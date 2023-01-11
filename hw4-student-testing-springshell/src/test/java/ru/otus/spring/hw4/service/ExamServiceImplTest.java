package ru.otus.spring.hw4.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import ru.otus.spring.hw4.config.AppProps;
import ru.otus.spring.hw4.dao.FAQDao;
import ru.otus.spring.hw4.domain.FAQ;
import ru.otus.spring.hw4.parser.Parser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class ExamServiceImplTest {
    @InjectMocks
    private ExamServiceImpl examServiceImpl;
    @Mock
    private MessageSource messageSource;

    @Mock
    private Parser parser;
    @Mock
    private AppProps appProps;

    @Mock
    private FAQDao faqDao;

    @BeforeEach
     void setUp() {
        Mockito.lenient().when(appProps.getLocale()).thenReturn(Locale.forLanguageTag("en"));
        Mockito.lenient().when(messageSource.getMessage(any(), any(), any())).thenReturn("");
    }
    @Test
    void startExamWithWrongType() {
        var faq = new FAQ();
        faq.setQuestionType("100");
        Mockito.lenient().when(parser.getFAQFromFile(FAQ.class))
                .thenReturn(List.of(faq));
        String inputName = "name \n 11 \n A \n";
        InputStream inN = new ByteArrayInputStream(inputName.getBytes());
        System.setIn(inN);
        var result = examServiceImpl.startExam();

        assertThat(result).isFalse();
    }

    @Test
    void startExamWithRightType() {
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
        var result = examServiceImpl.startExam();
        assertThat(result).isTrue();
    }
}
