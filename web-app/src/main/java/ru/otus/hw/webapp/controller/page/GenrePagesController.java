package ru.otus.hw.webapp.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class GenrePagesController {
    @GetMapping("/genres")
    public String listGenres() {
        return "list-genre";
    }

    @GetMapping("/genres/add")
    public String showAddForm() {
        return "add-genre";
    }

    @GetMapping("/genres/edit")
    public String showUpdateForm() {
        return "edit-genre";
    }
}
