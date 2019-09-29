package ru.otus.hw.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.hw.batch.domain.Book;
import ru.otus.hw.batch.domain.MongoAuthor;
import ru.otus.hw.batch.domain.MongoBook;

import javax.persistence.EntityManagerFactory;
import java.util.List;

@EnableBatchProcessing
@Configuration
public class BatchConfig {
    private final Logger logger = LoggerFactory.getLogger("Batch");
    private JobBuilderFactory jobBuilderFactory;
    private EntityManagerFactory entityManagerFactory;
    private StepBuilderFactory stepBuilderFactory;
    private MongoTemplate mongoTemplate;

    public BatchConfig(JobBuilderFactory jobBuilderFactory, EntityManagerFactory entityManagerFactory,
                       StepBuilderFactory stepBuilderFactory, MongoTemplate mongoTemplate) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.entityManagerFactory = entityManagerFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.mongoTemplate = mongoTemplate;
    }

    @Bean
    public JpaPagingItemReader<Book> itemReader() {
        return new JpaPagingItemReaderBuilder<Book>()
                .name("bookReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("select b from Books b")
                .pageSize(1000)
                .build();
    }

    @Bean
    public ItemProcessor<Book, MongoBook> processor() {
        return book -> {
            MongoBook mongoBook = new MongoBook();
            mongoBook.setName(book.getName());
            mongoBook.setGenre(book.getGenre().getName());
            mongoBook.setAuthor(new MongoAuthor(book.getAuthor().getFirstName(), book.getAuthor().getLastName()));
            return mongoBook;
        };
    }

    @Bean
    public MongoItemWriter<MongoBook> writer() {
        return new MongoItemWriterBuilder<MongoBook>()
                .template(mongoTemplate)
                .build();
    }

    @Bean
    public Job migrateBookJob(Step migrateBookStep) {
        return jobBuilderFactory.get("migrateBookJob")
                .incrementer(new RunIdIncrementer())
                .flow(migrateBookStep)
                .end()
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(JobExecution jobExecution) {
                        logger.info("Начало job");
                    }
                    @Override
                    public void afterJob(JobExecution jobExecution) {
                        logger.info("Конец job");
                        mongoTemplate.findAll(MongoBook.class).forEach(book -> logger.info(book.toString()));
                    }
                })
                .build();
    }

    @Bean
    public Step migrateBookStep(MongoItemWriter<MongoBook> writer, ItemReader<Book> reader, ItemProcessor<Book, MongoBook> itemProcessor) {
        return stepBuilderFactory.get("migrateBookStep")
                .<Book, MongoBook> chunk(5)
                .reader(reader)
                .processor(itemProcessor)
                .writer(writer)
                .listener(new ItemReadListener<Book> () {
                    public void beforeRead() { logger.info("Начало чтения"); }
                    public void afterRead(Book o) { logger.info("Конец чтения"); }
                    public void onReadError(Exception e) { logger.info("Ошибка чтения"); }
                })
                .listener(new ItemWriteListener<MongoBook>() {
                    public void beforeWrite(List list) { logger.info("Начало записи"); }
                    public void afterWrite(List list) { logger.info("Конец записи"); }
                    public void onWriteError(Exception e, List list) { logger.info("Ошибка записи"); }
                })
                .listener(new ItemProcessListener<Book, MongoBook>() {
                    public void beforeProcess(Book o) {logger.info("Начало обработки");}
                    public void afterProcess(Book o, MongoBook o2) {logger.info("Конец обработки");}
                    public void onProcessError(Book o, Exception e) {logger.info("Ошибка обработки");}
                })
                .listener(new ChunkListener() {
                    public void beforeChunk(ChunkContext chunkContext) {logger.info("Начало пачки");}
                    public void afterChunk(ChunkContext chunkContext) {logger.info("Конец пачки");}
                    public void afterChunkError(ChunkContext chunkContext) {logger.info("Ошибка пачки");}
                })
                .build();
    }
}
