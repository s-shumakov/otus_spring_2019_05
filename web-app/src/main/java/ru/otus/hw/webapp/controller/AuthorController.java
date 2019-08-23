package ru.otus.hw.webapp.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.otus.hw.webapp.domain.Author;
import ru.otus.hw.webapp.exception.CustomException;
import ru.otus.hw.webapp.repostory.AuthorRepository;

import java.util.List;

@Controller
@RequestMapping("/author")
public class AuthorController {
    private final AuthorRepository authorRepository;

    public AuthorController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @GetMapping
    public String listAuthors(Model model) {
        List<Author> authors = authorRepository.findAll();
        model.addAttribute("authors", authors);
        return "list-author";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        Author author = new Author();
        model.addAttribute("author", author);
        return "add-author";
    }

    @PostMapping("/add")
    public String addAuthor(@ModelAttribute("author") Author author, BindingResult result, ModelMap model) {
        if (result.hasErrors()) {
            return "add-author";
        }
        authorRepository.save(author);
        return "redirect:/author";
    }

    @GetMapping("/edit")
    public String showUpdateForm(@RequestParam("id") long id, Model model) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Author with Id: " + id + " not found"));
        model.addAttribute("author", author);
        return "edit-author";
    }

    @PostMapping("/edit")
    public String updateAuthor(@ModelAttribute("author") Author author, BindingResult result, ModelMap model) {
        if (result.hasErrors()) {
            return "edit-author";
        }
        authorRepository.save(author);
        return "redirect:/author";
    }

    @DeleteMapping("/delete")
    public String deleteAuthor(@RequestParam("id") long id, Model model) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid author Id:" + id));
        authorRepository.delete(author);
        try {
            authorRepository.delete(author);
        } catch (DataIntegrityViolationException e) {
            throw new CustomException("Could not delete author with id " + id);
        }
        return "redirect:/author";
    }
}
