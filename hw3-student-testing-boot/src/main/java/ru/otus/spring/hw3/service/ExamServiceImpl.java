package ru.otus.spring.hw3.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring.hw3.config.AppProps;
import ru.otus.spring.hw3.dao.FAQDao;
import ru.otus.spring.hw3.domain.FAQ;
import ru.otus.spring.hw3.domain.User;
import ru.otus.spring.hw3.exceptions.ParseFileException;
import ru.otus.spring.hw3.exceptions.QuestionTypeNotFound;
import ru.otus.spring.hw3.parser.Parser;

import java.util.List;
import java.util.Scanner;

@Service
public class ExamServiceImpl implements ExamService {
    private final FAQDao faqDao;
    private Parser parser;
    private final MessageSource messageSource;
    private final AppProps props;
    private static Logger log = LoggerFactory.getLogger(ExamServiceImpl.class);

    public ExamServiceImpl(FAQDao faqDao, Parser parser, MessageSource messageSource, AppProps props) {
        this.faqDao = faqDao;
        this.parser = parser;
        this.messageSource = messageSource;
        this.props = props;
    }

    @Override
    public boolean startExam() {
        var flag = true;
        try {
            List<FAQ> questions = parser.getFAQFromFile(FAQ.class);

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
            var user = User.builder().fio(fio).groupNumber(group).score(countRightAnswers).build();
            printResults(user, questions.size());
        } catch (ParseFileException | QuestionTypeNotFound e) {
            flag = false;
            log.error("Failed to start Exam, exception:", e);
        }
        return flag;

    }
    public void printInfo(String code, String[] args){
        System.out.println(messageSource.getMessage(code, args, props.getLocale()));
    }
    public void printResults(User user, int maxScore) {
        printInfo("user.info", new String[]{user.fio(), user.groupNumber(), user.score().toString()});
        printInfo("user.maxScore", new String[]{String.valueOf(maxScore)});
    }

}
