package ru.otus.spring01.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
public class ConfigProperties {
    private String questionsFilePath;
    private int answersCorrectNumber;
    private String locale;

    public String getQuestionsFilePath() {
        return questionsFilePath;
    }

    public void setQuestionsFilePath(String questionsFilePath) {
        this.questionsFilePath = questionsFilePath;
    }

    public int getAnswersCorrectNumber() {
        return answersCorrectNumber;
    }

    public void setAnswersCorrectNumber(int answersCorrectNumber) {
        this.answersCorrectNumber = answersCorrectNumber;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
}
