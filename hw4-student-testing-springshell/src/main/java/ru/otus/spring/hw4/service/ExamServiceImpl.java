package ru.otus.spring.hw4.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring.hw4.FileLoader;
import ru.otus.spring.hw4.config.AppProps;
import ru.otus.spring.hw4.config.ServicesConfig;
import ru.otus.spring.hw4.dao.FAQDao;
import ru.otus.spring.hw4.domain.FAQ;
import ru.otus.spring.hw4.domain.User;
import ru.otus.spring.hw4.exceptions.ParseFileException;
import ru.otus.spring.hw4.exceptions.QuestionTypeNotFound;
import ru.otus.spring.hw4.parser.Parser;

import java.util.List;
import java.util.Scanner;

@Service
@RequiredArgsConstructor
public class ExamServiceImpl implements ExamService {
    private final FAQDao faqDao;
    private final Parser parser;
    private final FileLoader fileLoader;
    private final ServicesConfig servicesConfig;
    private final MessageSource messageSource;
    private final AppProps props;
    private static final Logger log = LoggerFactory.getLogger(ExamServiceImpl.class);

    @Override
    public boolean startExam() {
        var flag = true;
        var file = fileLoader.getFileFromResource(servicesConfig.getName());

        try {
            List<FAQ> questions = parser.getFAQFromFile(FAQ.class, file);

            Scanner scanner = new Scanner(System.in);
            printInfo("user.name", new String[]{});
            var fio = scanner.nextLine();
            printInfo("user.group", new String[]{});
            var group = scanner.nextLine();
            var countRightAnswers = 0;
            for (FAQ question : questions) {
                System.out.println(question);
                if (faqDao.checkAnswer(question.getRightAnswer(), scanner.nextLine())) {
                    countRightAnswers++;
                }
            }
            scanner.close();
            var user = User
                    .builder()
                    .fio(fio)
                    .groupNumber(group)
                    .score(countRightAnswers)
                    .build();
            printResults(user, questions.size());
        } catch (ParseFileException | QuestionTypeNotFound e) {
            flag = false;
            log.error("Failed to start Exam, exception:", e);
        }
        return flag;

    }

    public void printInfo(String code, String[] args) {
        System.out.println(messageSource.getMessage(code, args, props.getLocale()));
    }

    public void printResults(User user, int maxScore) {
        printInfo("user.info", new String[]{user.fio(), user.groupNumber(), user.score().toString()});
        printInfo("user.maxScore", new String[]{String.valueOf(maxScore)});
    }

}
