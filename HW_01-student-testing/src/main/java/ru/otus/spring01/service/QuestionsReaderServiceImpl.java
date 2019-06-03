package ru.otus.spring01.service;

import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.core.io.ClassPathResource;
import ru.otus.spring01.domain.CsvQuestion;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionsReaderServiceImpl implements QuestionsReaderService {
    private String path;

    public QuestionsReaderServiceImpl(String path) {
        this.path = path;
    }

    @Override
    public List<CsvQuestion> readQuestions() {
        List<CsvQuestion> csvQuestions = new ArrayList<>();

        try (InputStream resource = new ClassPathResource(path).getInputStream()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(resource));
            csvQuestions = this.parseToBean(reader);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return csvQuestions;
    }

    public List<CsvQuestion> parseToBean(Reader reader) {
        return new CsvToBeanBuilder<CsvQuestion>(reader)
                .withType(CsvQuestion.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build().parse();
    }

}
