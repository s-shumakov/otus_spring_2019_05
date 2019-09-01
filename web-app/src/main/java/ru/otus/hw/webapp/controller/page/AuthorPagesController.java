package ru.otus.hw.webapp.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/authors")
public class AuthorPagesController {
    @GetMapping
    public String listAuthors() {
        return "list-author";
    }

    @GetMapping("/add")
    public String showAddForm() {
        return "add-author";
    }

    @GetMapping("/edit")
    public String showUpdateForm() {
        return "edit-author";
    }
}
