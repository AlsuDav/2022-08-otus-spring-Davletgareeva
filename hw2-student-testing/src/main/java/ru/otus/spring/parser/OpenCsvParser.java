package ru.otus.spring.parser;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import ru.otus.spring.domain.FAQ;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import java.util.List;

@Getter
@Setter
public class OpenCsvParser implements Parser {
    @Value("${testing.file.name}")
    private String fileName;

    @Override
    public List<FAQ> getFAQFromFile(Class clazz) throws URISyntaxException, IOException {

        return new CsvToBeanBuilder(new FileReader(getFileFromResource(fileName)))
                .withType(clazz)
                .build()
                .parse();

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
