package ru.otus.spring.parser;

import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.FAQ;
import ru.otus.spring.exceptions.ParseFileException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import java.util.List;

@Service
public class OpenCsvParser implements Parser {
    @Value("${testing.file.name}")
    private String fileName;

    @Override
    public List<FAQ> getFAQFromFile(Class clazz) {
        try {
            return new CsvToBeanBuilder(new FileReader(getFileFromResource(fileName)))
                    .withType(clazz)
                    .build()
                    .parse();
        } catch (IOException e) {
            throw new ParseFileException("Ошибка при попытке парсинга файла: %s".formatted(fileName), e);

        }

    }

    public File getFileFromResource(String fileName) {
        try {
            ClassLoader classLoader = OpenCsvParser.class.getClassLoader();
            URL resource = classLoader.getResource(fileName);
            if (resource == null) {
                throw new IllegalArgumentException("file not found! " + fileName);
            } else {
                return new File(resource.toURI());
            }
        } catch (URISyntaxException e) {
            throw new ParseFileException("Ошибка при попытке найти файл по имени: %s".formatted(fileName), e);
        }
    }


}
