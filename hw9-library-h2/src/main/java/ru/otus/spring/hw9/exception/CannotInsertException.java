package ru.otus.spring.hw9.exception;

public class CannotInsertException extends RuntimeException{
    public CannotInsertException(String message) {
        super(message);
    }

}
