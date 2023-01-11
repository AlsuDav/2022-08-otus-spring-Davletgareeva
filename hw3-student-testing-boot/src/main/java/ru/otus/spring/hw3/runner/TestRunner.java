package ru.otus.spring.hw3.runner;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.otus.spring.hw3.service.ExamService;
@Component
@RequiredArgsConstructor
public class TestRunner implements CommandLineRunner {

    private final ExamService examService;

    @Override
    public void run(String... args) {
        examService.startExam();

    }
}
