package ru.otus.hw.webapp.controller.page;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class GenrePagesController {
    @GetMapping("/genres")
    public String listGenres() {
        return "list-genre";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/genres/add")
    public String showAddForm() {
        return "add-genre";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/genres/edit")
    public String showUpdateForm() {
        return "edit-genre";
    }
}
