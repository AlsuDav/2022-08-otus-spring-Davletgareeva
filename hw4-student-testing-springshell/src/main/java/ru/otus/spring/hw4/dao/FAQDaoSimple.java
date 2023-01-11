package ru.otus.spring.hw4.dao;

import org.springframework.stereotype.Repository;

@Repository("faqDao")
public class FAQDaoSimple implements FAQDao {
    @Override
    public boolean checkAnswer(String userAnswer, String rightAnswer) {

        return userAnswer.equals(rightAnswer);

    }
}
