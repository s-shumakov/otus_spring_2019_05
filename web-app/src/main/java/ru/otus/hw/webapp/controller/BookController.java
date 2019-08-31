package ru.otus.hw.webapp.controller;

import org.springframework.web.bind.annotation.*;
import ru.otus.hw.webapp.domain.Book;
import ru.otus.hw.webapp.exception.CustomException;
import ru.otus.hw.webapp.repostory.AuthorRepository;
import ru.otus.hw.webapp.repostory.BookRepository;
import ru.otus.hw.webapp.repostory.GenreRepository;

import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BookController {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    public BookController(BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }

    @GetMapping
    public List<Book> listBooks() {
        return bookRepository.findAll();
    }

    @PostMapping("/add")
    public Book addBook(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    @GetMapping("/edit")
    public Book getBook(@RequestParam Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Book with Id: " + id + " not found"));
    }

    @PutMapping("/edit")
    public Book updateBook(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    @DeleteMapping("/delete")
    public void deleteBook(@RequestParam("id") long id) {
        try {
            bookRepository.deleteById(id);
        } catch (Exception e) {
            throw new CustomException("Could not delete book with id " + id);
        }
    }
}
