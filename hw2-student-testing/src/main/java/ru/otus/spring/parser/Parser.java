package ru.otus.spring.parser;

import ru.otus.spring.domain.FAQ;

import java.util.List;


public interface Parser {
    List<FAQ> getFAQFromFile(Class<? extends FAQ> clazz);
}
