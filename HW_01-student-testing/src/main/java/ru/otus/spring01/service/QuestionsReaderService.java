package ru.otus.spring01.service;

import ru.otus.spring01.domain.CsvQuestion;

import java.io.Reader;
import java.util.List;

public interface QuestionsReaderService {
    List<CsvQuestion> readQuestions();
}
