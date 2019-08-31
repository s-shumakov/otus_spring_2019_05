package ru.otus.hw.webapp.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/genre")
public class GenrePagesController {
    @GetMapping
    public String listGenres() {
        return "list-genre";
    }

    @GetMapping("/add")
    public String showAddForm() {
        return "add-genre";
    }

    @GetMapping("/edit")
    public String showUpdateForm() {
        return "edit-genre";
    }
}
