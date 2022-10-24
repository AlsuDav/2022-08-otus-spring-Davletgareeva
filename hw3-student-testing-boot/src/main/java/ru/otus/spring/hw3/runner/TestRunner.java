package ru.otus.spring.hw3.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.otus.spring.hw3.service.ExamService;
@Component
public class TestRunner implements CommandLineRunner {
    @Autowired
    ExamService examService;

    @Override
    public void run(String... args) {
        examService.startExam();

    }
}
