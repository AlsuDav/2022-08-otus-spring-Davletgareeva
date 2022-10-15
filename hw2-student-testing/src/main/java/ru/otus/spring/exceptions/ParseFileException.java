package ru.otus.spring.exceptions;

public class ParseFileException extends RuntimeException {

    public ParseFileException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
