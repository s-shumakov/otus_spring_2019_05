package ru.otus.hw.webapp.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BookPagesController {
    @GetMapping
    public String listBooks() {
        return "list-book";
    }

    @GetMapping("/add")
    public String showAddForm() {
        return "add-book";
    }

    @GetMapping("/edit")
    public String showUpdateForm() {
        return "edit-book";
    }
}
