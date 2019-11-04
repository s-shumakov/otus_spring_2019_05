package ru.otus.hw.webapp.endpoint;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;
import ru.otus.hw.webapp.repostory.AuthorRepository;
import ru.otus.hw.webapp.repostory.BookRepository;
import ru.otus.hw.webapp.repostory.GenreRepository;

import java.util.HashMap;
import java.util.Map;

@Component
@Endpoint(id = "library")
public class LibraryEndPoint {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    public LibraryEndPoint(BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }

    @ReadOperation
    public Map<String, Long> count() {
        Map<String, Long> map = new HashMap<>();
        map.put("Books", bookRepository.count());
        map.put("Authors", authorRepository.count());
        map.put("Genres", genreRepository.count());
        return map;
    }

}
