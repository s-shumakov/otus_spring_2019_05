package ru.otus.hw;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.otus.hw.dao.BookDao;
import ru.otus.hw.domain.Author;
import ru.otus.hw.domain.Book;
import ru.otus.hw.domain.Genre;

import java.sql.SQLException;

@SpringBootApplication
public class BookLibraryApplication {
    public static void main(String[] args) throws SQLException {
//        SpringApplication.run(BookLibraryApplication.class, args);
        ApplicationContext context = SpringApplication.run(BookLibraryApplication.class);
        BookDao dao = context.getBean(BookDao.class);

        Console.main(args);
        dao.findAll();
//        System.out.println(dao.findAll().size());
        System.out.println(dao.count());
        Book book = new Book(null, "test",
                new Author(null, "qwer", "asdf"),
                new Genre(2L, "sdfg"));
        dao.insert(book);
        System.out.println(dao.count());
    }
}
