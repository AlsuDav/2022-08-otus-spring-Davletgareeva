package ru.otus.spring.hw9.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Book {
    private final long id;
    private final String bookName;
    private final Author author;
    private Genre genre;

}
