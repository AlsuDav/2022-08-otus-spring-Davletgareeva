package ru.otus.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.spring.dao.FAQDao;
import ru.otus.spring.parser.Parser;
import ru.otus.spring.service.ExamService;
import ru.otus.spring.service.ExamServiceImpl;

@PropertySource("classpath:application.properties")
@Configuration
public class ServicesConfig {
    @Bean
    public ExamService examService(FAQDao dao, Parser parser) {
        return new ExamServiceImpl(dao, parser);
    }

}

