package ru.otus.hw.webapp.controller.page;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthorPagesController {
    @GetMapping("/authors")
    public String listAuthors() {
        return "list-author";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/authors/add")
    public String showAddForm() {
        return "add-author";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/authors/edit")
    public String showUpdateForm() {
        return "edit-author";
    }
}
