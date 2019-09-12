package ru.otus.hw.webapp.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.otus.hw.webapp.domain.Author;
import ru.otus.hw.webapp.exception.CustomException;
import ru.otus.hw.webapp.repostory.AuthorRepository;

import java.util.List;

@RestController
public class AuthorController {
    private final AuthorRepository authorRepository;

    public AuthorController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @GetMapping("/api/authors")
    public List<Author> listAuthors() {
        return authorRepository.findAll();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/api/authors")
    public Author addAuthor(@RequestBody Author author) {
        return authorRepository.save(author);
    }

    @GetMapping("/api/authors/{id}")
    public Author getAuthor(@PathVariable Long id) {
        return authorRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Genre with Id: " + id + " not found"));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/api/authors/{id}")
    public Author updateAuthor(@RequestBody Author author) {
        return authorRepository.save(author);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/api/authors/{id}")
    public void deleteAuthor(@PathVariable Long id) {
        try {
            authorRepository.deleteById(id);
        } catch (Exception e) {
            throw new CustomException("Could not delete author with id " + id);
        }
    }
}
