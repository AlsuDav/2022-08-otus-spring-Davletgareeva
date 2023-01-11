package ru.otus.spring.hw4.parser;

import ru.otus.spring.hw4.domain.FAQ;

import java.util.List;


public interface Parser {
    List<FAQ> getFAQFromFile(Class<? extends FAQ> clazz);
}
