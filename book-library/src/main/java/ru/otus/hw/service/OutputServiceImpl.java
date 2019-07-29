package ru.otus.hw.service;

import org.springframework.stereotype.Service;
import ru.otus.hw.domain.*;

import java.io.PrintStream;
import java.util.List;

@Service
public class OutputServiceImpl implements OutputService {
    private final PrintStream printStream;

    public OutputServiceImpl(ConsoleContext consoleContext) {
        this.printStream = consoleContext.getPrintStream();
    }

    @Override
    public void printAuthors(List<Author> authors) {
        authors.forEach(row -> {
            printStream.println(row.getId() + "\t" + row.getFirstName() + " " + row.getLastName());
        });
    }

    @Override
    public void printGenres(List<Genre> genres) {
        genres.forEach(row -> {
            printStream.println(row.getId() + "\t" + row.getGenreName());
        });
    }

    @Override
    public void printBooks(List<Book> books) {
        books.forEach(row -> {
            printStream.println(row.getId() + "\t\"" + row.getName() + "\""
                    + (row.getAuthor() != null ? " - " + row.getAuthor().getFirstName() + " " + row.getAuthor().getLastName() : "")
                    + (row.getGenre() != null ? ", " + row.getGenre().getGenreName() : ""));
        });
    }

    @Override
    public void printComments(List<Comment> comments) {
        comments.forEach(row -> {
            printStream.println(row.getComment());
        });
    }

    @Override
    public void println(String message) {
        printStream.println(message);
    }
}
