package ru.otus.hw.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@SpringBootApplication
public class BatchApplication {

    public static void main(String[] args) throws SQLException {
        SpringApplication.run(BatchApplication.class, args);
    }

}
