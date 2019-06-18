package ru.otus.spring01.service;

import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import ru.otus.spring01.config.ConfigProperties;
import ru.otus.spring01.domain.CsvQuestion;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class QuestionsReaderServiceImpl implements QuestionsReaderService {
    private String path;
    private Locale locale;

    public QuestionsReaderServiceImpl(
            ConfigProperties configProperties) {
        this.path = configProperties.getQuestionsFilePath();
        this.locale = new Locale(configProperties.getLocale());
    }

    @Override
    public List<CsvQuestion> readQuestions() {
        List<CsvQuestion> csvQuestions = new ArrayList<>();
        if (this.locale.getLanguage().equalsIgnoreCase("en")) {
            this.path = this.path.replace(".csv", "_" + locale.getLanguage() + ".csv");
        }
        try (InputStream resource = new ClassPathResource(path).getInputStream()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(resource));
            csvQuestions = this.parseToBean(reader);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return csvQuestions;
    }

    private List<CsvQuestion> parseToBean(Reader reader) {
        return new CsvToBeanBuilder<CsvQuestion>(reader)
                .withType(CsvQuestion.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build().parse();
    }

}
