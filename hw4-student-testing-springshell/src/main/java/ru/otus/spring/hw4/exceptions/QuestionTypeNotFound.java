package ru.otus.spring.hw4.exceptions;

public class QuestionTypeNotFound extends RuntimeException {

    public QuestionTypeNotFound(String message) {
        super(message);
    }
}
