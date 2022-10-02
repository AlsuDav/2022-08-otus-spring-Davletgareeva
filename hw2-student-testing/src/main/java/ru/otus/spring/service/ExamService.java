package ru.otus.spring.service;

import ru.otus.spring.parser.Parser;

import java.net.URISyntaxException;

public interface ExamService {
    boolean startExam(Parser parser) throws URISyntaxException;
}
