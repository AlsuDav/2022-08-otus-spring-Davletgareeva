package ru.otus.spring.hw9.exception;

public class CannotUpdateException extends RuntimeException {

    public CannotUpdateException(String message) {
        super(message);
    }

}
