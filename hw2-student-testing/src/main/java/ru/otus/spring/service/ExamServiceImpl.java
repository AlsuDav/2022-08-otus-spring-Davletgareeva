package ru.otus.spring.service;

import com.opencsv.exceptions.CsvException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.spring.dao.FAQDao;
import ru.otus.spring.domain.FAQ;
import ru.otus.spring.domain.User;
import ru.otus.spring.exceptions.QuestionTypeNotFound;
import ru.otus.spring.parser.Parser;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Scanner;

public class ExamServiceImpl implements ExamService {
    private final FAQDao faqDao;
    private final static String NAME_AND_SURNAME = "Введите свое имя и фамилию:";
    private final static String GROUP_NUMBER = "Введите свой номер группы:";
    private static Logger log = LoggerFactory.getLogger(ExamServiceImpl.class);
    public ExamServiceImpl(FAQDao faqDao) {
        this.faqDao = faqDao;
    }
    @Override
    public boolean startExam(Parser parser) throws URISyntaxException {
        var flag = true;
        try {
            List<FAQ> questions = parser.getFAQFromFile(FAQ.class);

            Scanner scanner = new Scanner(System.in);
            System.out.println(NAME_AND_SURNAME);
            var fio = scanner.nextLine();
            System.out.println(GROUP_NUMBER);
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
        } catch (IOException | CsvException | QuestionTypeNotFound e) {
            flag = false;
            log.error("Failed to start Exam, exception:", e);
        }
        return flag;

    }

    public void printResults(User user, int maxScore) {
        System.out.println("%s \n Max Score: %s".formatted(user, maxScore));
    }
}
