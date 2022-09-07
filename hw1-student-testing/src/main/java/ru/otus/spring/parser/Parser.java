package ru.otus.spring.parser;

import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;


public interface Parser {
    List<String[]> getContentFromFile() throws URISyntaxException, IOException, CsvException;
}
