package ru.otus.hw.webapp.controller;

import org.springframework.web.bind.annotation.*;
import ru.otus.hw.webapp.domain.Book;
import ru.otus.hw.webapp.exception.CustomException;
import ru.otus.hw.webapp.repostory.BookRepository;

import java.util.List;

@RestController
public class BookController {
    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/api/books")
    public List<Book> listBooks() {
        return bookRepository.findAll();
    }

    @PostMapping("/api/books")
    public Book addBook(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    @GetMapping("/api/books/{id}")
    public Book getBook(@PathVariable Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Book with Id: " + id + " not found"));
    }

    @PutMapping("/api/books/{id}")
    public Book updateBook(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    @DeleteMapping("/api/books/{id}")
    public void deleteBook(@PathVariable Long id) {
        try {
            bookRepository.deleteById(id);
        } catch (Exception e) {
            throw new CustomException("Could not delete book with id " + id);
        }
    }
}
