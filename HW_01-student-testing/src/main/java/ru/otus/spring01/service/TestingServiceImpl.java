package ru.otus.spring01.service;

import org.springframework.stereotype.Service;
import ru.otus.spring01.config.ConfigProperties;
import ru.otus.spring01.domain.CsvQuestion;

import java.util.*;

@Service
public class TestingServiceImpl implements TestingService {
    private final QuestionsReaderService questionsReaderService;
    private final InputOutputService inputOutputService;
    private int answersCorrectNumber;
    private Locale locale;
    private int trueAnswers = 0;
    public static final String ANSWERS_PATTERN = "[a-dA-D]";
    public static final String INPUT_NAME = "input.name";
    public static final String INCORRECT_INPUT_NAME = "incorrect.input.name";
    public static final String CHOOSE_ANSWER = "choose.answer";
    public static final String INCORRECT_INPUT_ANSWER = "incorrect.input.answer";
    public static final String CORRECT_ANSWERS_COUNT = "correct.answers.count";
    public static final String TEST_FAILED = "test.failed";
    public static final String TEST_PASSED = "test.passed";

    public TestingServiceImpl(
            QuestionsReaderService questionsReaderService,
            ConfigProperties configProperties,
            InputOutputService inputOutputService) {
        this.questionsReaderService = questionsReaderService;
        this.inputOutputService = inputOutputService;
        this.answersCorrectNumber = configProperties.getAnswersCorrectNumber();
        this.locale = new Locale(configProperties.getLocale());
    }

    @Override
    public void runTest() {
        inputOutputService.println(INPUT_NAME, this.locale);
        String line = inputOutputService.nextLine();
        while (line.isEmpty()) {
            inputOutputService.println(INCORRECT_INPUT_NAME, this.locale);
            line = inputOutputService.nextLine();
        }
        String userName = line;
        this.trueAnswers = 0;
        List<CsvQuestion> csvQuestions = this.questionsReaderService.readQuestions();
        for (CsvQuestion csvQuestion : csvQuestions) {
            inputOutputService.println(csvQuestion.getQuestion());
            inputOutputService.println(CHOOSE_ANSWER,  this.locale);
            csvQuestion.getAnswers().values().stream().sorted().forEach(inputOutputService::println);
            while (!inputOutputService.hasNext(ANSWERS_PATTERN)) {
                inputOutputService.println(INCORRECT_INPUT_ANSWER,  this.locale);
                inputOutputService.next();
            }
            String answer = inputOutputService.next();
            inputOutputService.println("");
            if (answer.equals(csvQuestion.getTrueAnswer())) {
                this.trueAnswers++;
            }
        }
        inputOutputService.println(
                CORRECT_ANSWERS_COUNT,
                new String[]{userName, String.valueOf(this.trueAnswers), String.valueOf(csvQuestions.size())},
                this.locale
        );
        if (trueAnswers < answersCorrectNumber) {
            inputOutputService.println(TEST_FAILED, this.locale);
        } else {
            inputOutputService.println(TEST_PASSED, this.locale);
        }
    }

    @Override
    public int getTrueAnswers() {
        return trueAnswers;
    }
}
