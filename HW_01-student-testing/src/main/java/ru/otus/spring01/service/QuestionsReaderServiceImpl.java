package ru.otus.spring01.service;

import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import ru.otus.spring01.domain.CsvQuestion;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class QuestionsReaderServiceImpl implements QuestionsReaderService {
    @Value("${questions.file.path}")
    private String path;
    @Value("${locale}")
    private Locale locale;

    @Override
    public List<CsvQuestion> readQuestions() {
        List<CsvQuestion> csvQuestions = new ArrayList<>();
        if(this.locale.getLanguage().equalsIgnoreCase("en")){
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

    public List<CsvQuestion> parseToBean(Reader reader) {
        return new CsvToBeanBuilder<CsvQuestion>(reader)
                .withType(CsvQuestion.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build().parse();
    }

}
