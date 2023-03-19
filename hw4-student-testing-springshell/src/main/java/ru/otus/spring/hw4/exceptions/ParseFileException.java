package ru.otus.spring.hw4.exceptions;

public class ParseFileException extends RuntimeException {

    public ParseFileException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
