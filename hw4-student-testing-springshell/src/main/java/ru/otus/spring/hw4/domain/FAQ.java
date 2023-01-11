package ru.otus.spring.hw4.domain;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public final class FAQ{
    @CsvBindByPosition(position = 0)
    private String questionType;
    @CsvBindByPosition(position = 1)
    private String question;
    @CsvBindByPosition(position = 2)
    private String answerA;
    @CsvBindByPosition(position = 3)
    private String answerB;
    @CsvBindByPosition(position = 4)
    private String answerC;
    @CsvBindByPosition(position = 5)
    private String answerD;
    @CsvBindByPosition(position = 6)
    private String rightAnswer;


    @Override
    public String toString() {
        var questionTypeEnum = QuestionType.fromQuestionType(this.questionType);
        if (questionTypeEnum.equals(QuestionType.TYPE_4)) {
            return " %s \n %s \n %s \n %s \n %s "
                    .formatted(this.question, this.answerA, this.answerB, this.answerC, this.answerD);
        } else if (questionTypeEnum.equals(QuestionType.TYPE_0)) {
            return " %s \n "
                    .formatted(this.question);
        }
        return "";
    }


}
