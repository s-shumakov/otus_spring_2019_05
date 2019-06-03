package ru.otus.spring01.domain;

import com.opencsv.bean.CsvBindByName;

public class CsvQuestion {
    @CsvBindByName
    private String question;

    @CsvBindByName
    private String answerA;

    @CsvBindByName
    private String answerB;

    @CsvBindByName
    private String answerC;

    @CsvBindByName
    private String answerD;

    @CsvBindByName
    private String trueAnswer;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswerA() {
        return answerA;
    }

    public void setAnswerA(String answerA) {
        this.answerA = answerA;
    }

    public String getAnswerB() {
        return answerB;
    }

    public void setAnswerB(String answerB) {
        this.answerB = answerB;
    }

    public String getAnswerC() {
        return answerC;
    }

    public void setAnswerC(String answerC) {
        this.answerC = answerC;
    }

    public String getAnswerD() {
        return answerD;
    }

    public void setAnswerD(String answerD) {
        this.answerD = answerD;
    }

    public String getTrueAnswer() {
        return trueAnswer;
    }

    public void setTrueAnswer(String trueAnswer) {
        this.trueAnswer = trueAnswer;
    }
}
