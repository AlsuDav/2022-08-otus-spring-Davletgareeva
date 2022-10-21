package ru.otus.spring.hw1.parser;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import lombok.Getter;
import lombok.Setter;
import ru.otus.spring.hw1.domain.FAQ;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class OpenCsvParser implements Parser {
    private String fileName;

    @Override
    public List<String[]> getContentFromFile() throws URISyntaxException, IOException, CsvException {

        CSVParser csvParser = new CSVParserBuilder().withSeparator(';').build(); // custom separator
        List<String[]> content;
        try (CSVReader reader = new CSVReaderBuilder(
                new FileReader(getFileFromResource(fileName)))
                .withCSVParser(csvParser)
                .build()) {
            content = reader.readAll();
        }
        return content;

    }

    public void printContent(List<String[]> content) {
        content.forEach(x -> System.out.println(new FAQ(new ArrayList<String>(Arrays.asList(x)))));

    }

    private static File getFileFromResource(String fileName) throws URISyntaxException {

        ClassLoader classLoader = OpenCsvParser.class.getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {


            return new File(resource.toURI());
        }
    }


}
