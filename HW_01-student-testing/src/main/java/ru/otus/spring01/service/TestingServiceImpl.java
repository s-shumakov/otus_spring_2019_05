package ru.otus.spring01.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring01.config.ConfigProperties;
import ru.otus.spring01.domain.CsvQuestion;

import java.util.*;

@Service
public class TestingServiceImpl implements TestingService {
    private final QuestionsReaderService questionsReaderService;
    private final MessageSource messageSource;
    private int answersCorrectNumber;
    private Locale locale;
    private static final String ANSWERS_PATTERN = "[a-dA-D]";
    private static final String INPUT_NAME = "input.name";
    private static final String INCORRECT_INPUT_NAME = "incorrect.input.name";
    private static final String CHOOSE_ANSWER = "choose.answer";
    private static final String INCORRECT_INPUT_ANSWER = "incorrect.input.answer";
    private static final String CORRECT_ANSWERS_COUNT = "correct.answers.count";
    private static final String TEST_FAILED = "test.failed";
    private static final String TEST_PASSED = "test.passed";

    public TestingServiceImpl(
            QuestionsReaderService questionsReaderService,
            MessageSource messageSource,
            ConfigProperties configProperties) {
        this.questionsReaderService = questionsReaderService;
        this.messageSource = messageSource;
        this.answersCorrectNumber = configProperties.getAnswersCorrectNumber();
        this.locale = new Locale(configProperties.getLocale());
    }

    @Override
    public void runTest() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(messageSource.getMessage(INPUT_NAME, null, this.locale));
        String line = scanner.nextLine().trim();
        while (line.isEmpty()) {
            System.out.println(messageSource.getMessage(INCORRECT_INPUT_NAME, null, this.locale));
            line = scanner.nextLine().trim();
        }
        String userName = line;
        int trueAnswers = 0;
        List<CsvQuestion> csvQuestions = this.questionsReaderService.readQuestions();
        for (CsvQuestion csvQuestion : csvQuestions) {
            System.out.println(csvQuestion.getQuestion());
            System.out.println(messageSource.getMessage(CHOOSE_ANSWER, null, this.locale));
            csvQuestion.getAnswers().values().stream().sorted().forEach(System.out::println);
            while (!scanner.hasNext(ANSWERS_PATTERN)) {
                System.out.println(messageSource.getMessage(INCORRECT_INPUT_ANSWER, null, this.locale));
                scanner.next();
            }
            String answer = scanner.next();
            System.out.println();
            if (answer.equals(csvQuestion.getTrueAnswer())) {
                trueAnswers++;
            }
        }
        System.out.println(
                messageSource.getMessage(
                        CORRECT_ANSWERS_COUNT,
                        new String[]{userName, String.valueOf(trueAnswers), String.valueOf(csvQuestions.size())},
                        this.locale)
        );
        if (trueAnswers < answersCorrectNumber) {
            System.out.println(messageSource.getMessage(TEST_FAILED, null, this.locale));
        } else {
            System.out.println(messageSource.getMessage(TEST_PASSED, null, this.locale));
        }
    }
}
