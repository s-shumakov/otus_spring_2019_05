package ru.otus.hw.webapp.controller;

import org.springframework.web.bind.annotation.*;
import ru.otus.hw.webapp.domain.Author;
import ru.otus.hw.webapp.exception.CustomException;
import ru.otus.hw.webapp.repostory.AuthorRepository;

import java.util.List;

@RestController
@RequestMapping("/api/author")
public class AuthorController {
    private final AuthorRepository authorRepository;

    public AuthorController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @GetMapping
    public List<Author> listAuthors() {
        return authorRepository.findAll();
    }

    @PostMapping("/add")
    public Author addAuthor(@RequestBody Author author) {
        return authorRepository.save(author);
    }

    @GetMapping("/edit")
    public Author getAuthor(@RequestParam Long id) {
        return authorRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Genre with Id: " + id + " not found"));
    }

    @PutMapping("/edit")
    public Author updateAuthor(@RequestBody Author author) {
        return authorRepository.save(author);
    }

    @DeleteMapping("/delete")
    public void deleteAuthor(@RequestParam("id") long id) {
        try {
            authorRepository.deleteById(id);
        } catch (Exception e) {
            throw new CustomException("Could not delete author with id " + id);
        }
    }
}
