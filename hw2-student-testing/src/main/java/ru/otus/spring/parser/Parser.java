package ru.otus.spring.parser;

import com.opencsv.exceptions.CsvException;
import ru.otus.spring.domain.FAQ;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;


public interface Parser {
    List<FAQ> getFAQFromFile(Class<? extends FAQ> clazz) throws URISyntaxException, IOException, CsvException;
}
