package ru.otus.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.parser.OpenCsvParser;
import ru.otus.spring.service.ExamService;

import java.net.URISyntaxException;

@Configuration
@ComponentScan
public class StudentTestStarter {

    public static void main(String[] args) throws URISyntaxException {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(StudentTestStarter.class);

        var parser = context.getBean(OpenCsvParser.class);
        ExamService examService = context.getBean(ExamService.class);
        examService.startExam(parser);

    }

}



