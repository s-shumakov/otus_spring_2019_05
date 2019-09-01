package ru.otus.hw.webapp.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthorPagesController {
    @GetMapping("/authors")
    public String listAuthors() {
        return "list-author";
    }

    @GetMapping("/authors/add")
    public String showAddForm() {
        return "add-author";
    }

    @GetMapping("/authors/edit")
    public String showUpdateForm() {
        return "edit-author";
    }
}
