package ru.otus.spring.domain;

import lombok.Builder;

@Builder
public record User(
        String fio,
        String groupNumber,
        Integer score
) {
    @Override
    public String toString() {
        return "Name: %s \n Group number: %s \n Score: %s".formatted(fio, groupNumber, score.toString());
    }

}
