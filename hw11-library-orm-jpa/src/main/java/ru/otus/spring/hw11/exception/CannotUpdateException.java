package ru.otus.spring.hw11.exception;

public class CannotUpdateException extends RuntimeException {

    public CannotUpdateException(String message) {
        super(message);
    }

}
