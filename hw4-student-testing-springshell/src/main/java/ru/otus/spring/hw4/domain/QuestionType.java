package ru.otus.spring.hw4.domain;

import ru.otus.spring.hw4.exceptions.QuestionTypeNotFound;

import java.util.Objects;

public enum QuestionType{
    TYPE_0("0"),
    TYPE_4("4");
    private final String type;

    QuestionType(String type) {
        this.type = type;
    }

    public static QuestionType fromQuestionType(String type) {
        for (QuestionType questionType : values()) {
            if (Objects.equals(type, questionType.type)) {
                return questionType;
            }
        }
        throw new QuestionTypeNotFound(
                "Не удалось определить тип вопроса по номеру '%s'".formatted(type)
        );
    }

}
