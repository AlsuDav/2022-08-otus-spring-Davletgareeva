package ru.otus.spring.hw3.parser;

import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.hw3.config.ServicesConfig;
import ru.otus.spring.hw3.domain.FAQ;
import ru.otus.spring.hw3.exceptions.ParseFileException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

@Service
public class OpenCsvParser implements Parser {
    @Autowired
    private ServicesConfig servicesConfig;

    @Override
    public List<FAQ> getFAQFromFile(Class clazz) {
        try {
            return new CsvToBeanBuilder(new FileReader(getFileFromResource(servicesConfig.getName())))
                    .withType(clazz)
                    .build()
                    .parse();
        } catch (IOException e) {
            throw new ParseFileException("Ошибка при попытке парсинга файла: %s".formatted(servicesConfig.getName()), e);

        }

    }

    private File getFileFromResource(String fileName) {
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
