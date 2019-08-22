package ru.otus.hw.webapp.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.otus.hw.webapp.domain.Genre;
import ru.otus.hw.webapp.exception.CustomException;
import ru.otus.hw.webapp.repostory.GenreRepository;

import java.util.List;

@Controller
@RequestMapping("/genre")
public class GenreController {
    private final GenreRepository genreRepository;

    public GenreController(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @GetMapping
    public String listGenres(Model model) {
        List<Genre> genres = genreRepository.findAll();
        model.addAttribute("genres", genres);
        return "list-genre";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        Genre genre = new Genre();
        model.addAttribute("genre", genre);
        return "add-genre";
    }

    @PostMapping("/add")
    public String addGenre(@ModelAttribute("genre") Genre genre, BindingResult result, ModelMap model) {
        if (result.hasErrors()) {
            return "add-genre";
        }
        genreRepository.save(genre);
        return "redirect:/genre";
    }

    @GetMapping("/edit")
    public String showUpdateForm(@RequestParam("id") long id, Model model) {
        Genre genre = genreRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Genre with Id: " + id + " not found"));
        model.addAttribute("genre", genre);
        return "edit-genre";
    }

    @PostMapping("/edit")
    public String updateGenre(@ModelAttribute("genre") Genre genre, BindingResult result, ModelMap model) {
        if (result.hasErrors()) {
            return "edit-genre";
        }
        genreRepository.save(genre);
        return "redirect:/genre";
    }

    @DeleteMapping("/delete")
    public String deleteGenre(@RequestParam("id") long id, Model model) {
        Genre genre = genreRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid genre Id:" + id));
        try {
            genreRepository.delete(genre);
        } catch (DataIntegrityViolationException e) {
            throw new CustomException("Could not delete genre with id " + id);
        }
        return "redirect:/genre";
    }
}
