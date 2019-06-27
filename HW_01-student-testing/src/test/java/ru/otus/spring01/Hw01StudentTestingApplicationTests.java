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
        MultiValuedMap<String, String> answers = new ArrayListValuedHashMap<>();
        answers.put("answer", "a. test answer");
        CsvQuestion csvQuestion = new CsvQuestion("Test question", answers, "a");
        questions.add(csvQuestion);

        given(inputOutputService.nextLine()).willReturn("Test User");
        given(inputOutputService.hasNext(TestingServiceImpl.ANSWERS_PATTERN)).willReturn(true);
        given(inputOutputService.next()).willReturn("a");
        given(questionsReaderService.readQuestions()).willReturn(questions);

        testingService.runTest();

        assertThat(testingService.getTrueAnswers()).isEqualTo(configProperties.getAnswersCorrectNumber());
    }

}
