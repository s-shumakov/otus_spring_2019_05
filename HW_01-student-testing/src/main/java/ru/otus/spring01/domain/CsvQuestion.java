package ru.otus.spring01.domain;

import com.opencsv.bean.CsvBindAndJoinByName;
import com.opencsv.bean.CsvBindByName;
import org.apache.commons.collections4.MultiValuedMap;

public class CsvQuestion {
    @CsvBindByName
    private String question;

    @CsvBindAndJoinByName(column = "answer", elementType = String.class)
    private MultiValuedMap<String, String> answers;

    @CsvBindByName
    private String trueAnswer;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getTrueAnswer() {
        return trueAnswer;
    }

    public void setTrueAnswer(String trueAnswer) {
        this.trueAnswer = trueAnswer;
    }

    public MultiValuedMap<String, String> getAnswers() {
        return answers;
    }

    public void setAnswers(MultiValuedMap<String, String> answers) {
        this.answers = answers;
    }
}
