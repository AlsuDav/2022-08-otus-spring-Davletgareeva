package ru.otus.spring.hw4.parser;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.hw4.domain.FAQ;
import ru.otus.spring.hw4.exceptions.ParseFileException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OpenCsvParser implements Parser {

    @Override
    public List<FAQ> getFAQFromFile(Class clazz, File file) {
        try {
            return new CsvToBeanBuilder(new FileReader(file))
                    .withType(clazz)
                    .build()
                    .parse();
        } catch (IOException e) {
            throw new ParseFileException("Ошибка при попытке парсинга файла: %s".formatted(file.getName()), e);
        }

    }
}