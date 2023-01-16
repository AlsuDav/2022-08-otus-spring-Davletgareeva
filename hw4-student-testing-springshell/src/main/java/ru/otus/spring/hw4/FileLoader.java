package ru.otus.spring.hw4;

import org.springframework.stereotype.Service;
import ru.otus.spring.hw4.exceptions.ParseFileException;
import ru.otus.spring.hw4.parser.Parser;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

@Service
public class FileLoader {

    public File getFileFromResource(String fileName) {
        try {
            ClassLoader classLoader = Parser.class.getClassLoader();
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
