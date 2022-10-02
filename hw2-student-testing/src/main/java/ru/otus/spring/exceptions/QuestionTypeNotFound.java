package ru.otus.spring.exceptions;

public class QuestionTypeNotFound extends RuntimeException {

    public QuestionTypeNotFound(String message) {
        super(message);
    }
}
