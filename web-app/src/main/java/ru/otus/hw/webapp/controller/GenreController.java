package ru.otus.hw.webapp.controller;

import org.springframework.web.bind.annotation.*;
import ru.otus.hw.webapp.domain.Genre;
import ru.otus.hw.webapp.exception.CustomException;
import ru.otus.hw.webapp.repostory.GenreRepository;

import java.util.List;

@RestController
@RequestMapping("/api/genres")
public class GenreController {
    private final GenreRepository genreRepository;

    public GenreController(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @GetMapping
    public List<Genre> listGenres() {
        return genreRepository.findAll();
    }

    @GetMapping("/{id}")
    public Genre getGenre(@PathVariable Long id) {
        return genreRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Genre with Id: " + id + " not found"));
    }

    @PostMapping
    public Genre addGenre(@RequestBody Genre genre) {
        return genreRepository.save(genre);
    }

    @PutMapping("/{id}")
    public Genre updateGenre(@RequestBody Genre genre) {
        return genreRepository.save(genre);
    }

    @DeleteMapping("/{id}")
    public void deleteGenre(@PathVariable Long id) {
        try {
            genreRepository.deleteById(id);
        } catch (Exception e) {
            throw new CustomException("Could not delete genre with id " + id);
        }
    }
}
