package ru.otus.spring01;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.spring01.config.ConfigProperties;
import ru.otus.spring01.domain.CsvQuestion;
import ru.otus.spring01.service.InputOutputService;
import ru.otus.spring01.service.QuestionsReaderService;
import ru.otus.spring01.service.TestingService;
import ru.otus.spring01.service.TestingServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
@ActiveProfiles("test")
public class Hw01StudentTestingApplicationTests {
    @MockBean
    private QuestionsReaderService questionsReaderService;
    @MockBean
    private MessageSource messageSource;
    @MockBean
    private InputOutputService inputOutputService;
    @Autowired
    private ConfigProperties configProperties;
    @Autowired
    private TestingService testingService;

    @Test
    public void runTestTest() {
        List<CsvQuestion> questions = new ArrayList<>();
        CsvQuestion csvQuestion = new CsvQuestion();
        csvQuestion.setQuestion("Test question");
        csvQuestion.setTrueAnswer("a");
        MultiValuedMap<String, String> answers = new ArrayListValuedHashMap<>();
        answers.put("answer", "a. test answer");
        answers.put("answer", "b. test answer");
        answers.put("answer", "c. test answer");
        answers.put("answer", "d. test answer");
        csvQuestion.setAnswers(answers);
        questions.add(csvQuestion);

        String name = "Test User";
        Locale locale = new Locale("ru");

        given(inputOutputService.nextLine()).willReturn(name);
        given(inputOutputService.hasNext(TestingServiceImpl.ANSWERS_PATTERN)).willReturn(true);
        given(inputOutputService.next()).willReturn("a");
        given(questionsReaderService.readQuestions()).willReturn(questions);
        given(this.messageSource.getMessage(TestingServiceImpl.INPUT_NAME, null, locale))
                .willReturn("Введите имя:");
        given(this.messageSource.getMessage(TestingServiceImpl.CHOOSE_ANSWER, null, locale))
                .willReturn("Выберите правильный ответ (a, b, c, d):");
        given(this.messageSource.getMessage(
                TestingServiceImpl.CORRECT_ANSWERS_COUNT,
                new String[]{name, String.valueOf(1), String.valueOf(1)},
                locale))
                .willReturn(name + ", количество правильных ответов: 1 из " +
                        configProperties.getAnswersCorrectNumber());
        given(this.messageSource.getMessage(TestingServiceImpl.TEST_PASSED, null, locale))
                .willReturn("Тест пройден!");

        testingService.runTest();

        assertThat(testingService.getTrueAnswers()).isEqualTo(configProperties.getAnswersCorrectNumber());
    }

}
