package ru.otus.spring.hw1.domain;

import java.util.List;

public class FAQ {
    private String question;
    private List<String> answers;

    public FAQ(List<String> questionsAndAnswers) {
        this.question = questionsAndAnswers.get(0);
        questionsAndAnswers.remove(0);
        this.answers = questionsAndAnswers;
    }

    @Override
    public String toString() {
        return "Question: %s \n Answers: %s".formatted(this.question, this.answers.toString());
    }
}
