package ru.otus.spring.hw3.dao;

public interface FAQDao {
    boolean checkAnswer(String userAnswer, String rightAnswer);
}
