package ru.otus.hw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@SpringBootApplication
public class BookLibraryApplication {
    public static void main(String[] args) throws SQLException {
        SpringApplication.run(BookLibraryApplication.class, args);
    }
}
