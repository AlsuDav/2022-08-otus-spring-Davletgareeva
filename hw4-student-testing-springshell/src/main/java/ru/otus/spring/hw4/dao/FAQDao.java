package ru.otus.spring.hw4.dao;

public interface FAQDao {
    boolean checkAnswer(String userAnswer, String rightAnswer);
}
