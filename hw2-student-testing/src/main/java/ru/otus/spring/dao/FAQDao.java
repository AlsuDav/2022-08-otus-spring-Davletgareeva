package ru.otus.spring.dao;

public interface FAQDao {
    boolean checkAnswer(String userAnswer, String rightAnswer);
}
