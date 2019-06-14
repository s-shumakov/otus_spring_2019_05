package ru.otus.spring01.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring01.domain.CsvQuestion;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;

@Service
public class TestingServiceImpl implements TestingService {
    private QuestionsReaderService questionsReaderService;
    private MessageSource messageSource;
    @Value("${answers.correct.number}")
    private int correctAnswersNumber;
    @Value("${locale}")
    private Locale locale;

    public TestingServiceImpl(
            QuestionsReaderService questionsReaderService,
            MessageSource messageSource) {
        this.questionsReaderService = questionsReaderService;
        this.messageSource = messageSource;
    }

    @Override
    public void runTest() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(messageSource.getMessage("input.name", null, this.locale));
        String userName = scanner.nextLine();
        int trueAnswers = 0;
        List<CsvQuestion> csvQuestions = this.questionsReaderService.readQuestions();
        for (CsvQuestion csvQuestion : csvQuestions) {
            System.out.println(csvQuestion.getQuestion());
            System.out.println(messageSource.getMessage("choose.answer", null, this.locale));
            csvQuestion.getAnswers().values().stream().sorted().forEach(System.out::println);
            String answer = scanner.nextLine();
            System.out.println();
            if (answer.equals(csvQuestion.getTrueAnswer())) {
                trueAnswers++;
            }
        }
        System.out.println(
                messageSource.getMessage(
                        "correct.answers.count", new String[]{
                                userName, String.valueOf(trueAnswers), String.valueOf(csvQuestions.size())
                        }, this.locale));
        if (trueAnswers < correctAnswersNumber) {
            System.out.println(messageSource.getMessage("test.failed", null, this.locale));
        } else {
            System.out.println(messageSource.getMessage("test.passed", null, this.locale));
        }
    }
}
