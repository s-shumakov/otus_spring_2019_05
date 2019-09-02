package ru.otus.hw.webapp.controller;

import org.springframework.web.bind.annotation.*;
import ru.otus.hw.webapp.domain.Genre;
import ru.otus.hw.webapp.exception.CustomException;
import ru.otus.hw.webapp.repostory.GenreRepository;

import java.util.List;

@RestController
public class GenreController {
    private final GenreRepository genreRepository;

    public GenreController(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @GetMapping("/api/genres")
    public List<Genre> listGenres() {
        return genreRepository.findAll();
    }

    @GetMapping("/api/genres/{id}")
    public Genre getGenre(@PathVariable Long id) {
        return genreRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Genre with Id: " + id + " not found"));
    }

    @PostMapping("/api/genres")
    public Genre addGenre(@RequestBody Genre genre) {
        return genreRepository.save(genre);
    }

    @PutMapping("/api/genres/{id}")
    public Genre updateGenre(@RequestBody Genre genre) {
        return genreRepository.save(genre);
    }

    @DeleteMapping("/api/genres/{id}")
    public void deleteGenre(@PathVariable Long id) {
        try {
            genreRepository.deleteById(id);
        } catch (Exception e) {
            throw new CustomException("Could not delete genre with id " + id);
        }
    }
}
