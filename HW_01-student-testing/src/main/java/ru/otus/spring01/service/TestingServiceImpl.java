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
    private int questionsCount = 0;
    private Map<String, Integer> results = new HashMap<>();
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
    }

    @Override
    public void runTest(String userName) {
        Integer trueAnswers = 0;
        List<CsvQuestion> csvQuestions = this.questionsReaderService.readQuestions();
        this.setQuestionsCount(csvQuestions.size());
        for (CsvQuestion csvQuestion : csvQuestions) {
            inputOutputService.println(csvQuestion.getQuestion());
            inputOutputService.printMessage(CHOOSE_ANSWER);
            csvQuestion.getAnswers().values().stream().sorted().forEach(inputOutputService::println);
            while (!inputOutputService.hasNext(ANSWERS_PATTERN)) {
                inputOutputService.printMessage(INCORRECT_INPUT_ANSWER);
                inputOutputService.next();
            }
            String answer = inputOutputService.next();
            inputOutputService.println("");
            if (answer.equals(csvQuestion.getTrueAnswer())) {
                trueAnswers++;
            }
        }
        this.getResults().put(userName, trueAnswers);
    }

    @Override
    public Integer getResult(String userName) {
        Integer correctAnswers = this.getResults().get(userName);
        inputOutputService.printMessage(
                CORRECT_ANSWERS_COUNT,
                new String[]{userName,
                        String.valueOf(correctAnswers),
                        String.valueOf(this.getQuestionsCount())
                }
        );
        if (correctAnswers < this.getAnswersCorrectNumber()) {
            inputOutputService.printMessage(TEST_FAILED);
        } else {
            inputOutputService.printMessage(TEST_PASSED);
        }
        return correctAnswers;
    }

    @Override
    public String getUserName() {
        inputOutputService.printMessage(INPUT_NAME);
        String userName = inputOutputService.nextLine();
        while (userName.isEmpty()) {
            inputOutputService.printMessage(INCORRECT_INPUT_NAME);
            userName = inputOutputService.nextLine();
        }
        return userName;
    }

    public Map<String, Integer> getResults() {
        return results;
    }

    public void setResults(Map<String, Integer> results) {
        this.results = results;
    }

    public int getQuestionsCount() {
        return questionsCount;
    }

    public void setQuestionsCount(int questionsCount) {
        this.questionsCount = questionsCount;
    }

    public int getAnswersCorrectNumber() {
        return answersCorrectNumber;
    }

}
