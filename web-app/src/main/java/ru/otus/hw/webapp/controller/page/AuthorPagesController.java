package ru.otus.hw.webapp.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.otus.hw.webapp.repostory.AuthorRepository;

@Controller
public class AuthorPagesController {
    private final AuthorRepository authorRepository;

    public AuthorPagesController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @GetMapping("/author")
    public String listAuthors() {
        return "list-author";
    }

    @GetMapping("/author/add")
    public String showAddForm() {
        return "add-author";
    }

    @GetMapping("author/edit")
    public String showUpdateForm() {
        return "edit-author";
    }
}
