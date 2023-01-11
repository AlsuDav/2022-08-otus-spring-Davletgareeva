package ru.otus.spring.hw4.domain;

import lombok.Builder;

@Builder
public record User(
        String fio,
        String groupNumber,
        Integer score
) {

}
