package ru.otus.spring.hw3.parser;

import ru.otus.spring.hw3.domain.FAQ;

import java.util.List;


public interface Parser {
    List<FAQ> getFAQFromFile(Class<? extends FAQ> clazz);
}
