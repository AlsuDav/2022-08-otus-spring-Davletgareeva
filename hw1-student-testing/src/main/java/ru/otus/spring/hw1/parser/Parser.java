package ru.otus.spring.hw1.parser;

import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;


public interface Parser {
    List<String[]> getContentFromFile() throws URISyntaxException, IOException, CsvException;
}
