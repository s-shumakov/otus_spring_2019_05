package ru.otus.spring01.service;

import ru.otus.spring01.domain.CsvQuestion;

import java.util.List;
import java.util.Scanner;

public class TestingServiceImpl implements TestingService {
    private QuestionsReaderService questionsReaderService;

    public TestingServiceImpl(QuestionsReaderService questionsReaderService) {
        this.questionsReaderService = questionsReaderService;
    }

    @Override
    public void runTest() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите фамилию и имя: ");
        String userName = scanner.nextLine();
        int trueAnswers = 0;
        List<CsvQuestion> csvQuestions = this.questionsReaderService.readQuestions();
        for (CsvQuestion csvQuestion : csvQuestions) {
            System.out.println(csvQuestion.getQuestion());
            System.out.println("Выберите правильный ответ (a, b, c, d):");
            System.out.println(csvQuestion.getAnswerA());
            System.out.println(csvQuestion.getAnswerB());
            System.out.println(csvQuestion.getAnswerC());
            System.out.println(csvQuestion.getAnswerD());
            String answer = scanner.nextLine();
            if (answer.equals(csvQuestion.getTrueAnswer())) {
                trueAnswers++;
            }
        }
        System.out.println(userName + ", количество правильных ответов: " + trueAnswers + " из " + csvQuestions.size());
    }
}
