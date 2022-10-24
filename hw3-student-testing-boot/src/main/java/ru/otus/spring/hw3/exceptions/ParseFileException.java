package ru.otus.spring.hw3.exceptions;

public class ParseFileException extends RuntimeException {

    public ParseFileException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
