package ru.otus.spring.hw11.exception;

public class CannotInsertException extends RuntimeException{
    public CannotInsertException(String message) {
        super(message);
    }

}
