package ru.otus.spring.hw3.exceptions;

public class QuestionTypeNotFound extends RuntimeException {

    public QuestionTypeNotFound(String message) {
        super(message);
    }
}
