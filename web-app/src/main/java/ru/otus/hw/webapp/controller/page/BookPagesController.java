package ru.otus.hw.webapp.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class BookPagesController {
    @GetMapping({"/", "/books"})
    public String listBooks() {
        return "list-book";
    }

    @GetMapping("/books/add")
    public String showAddForm() {
        return "add-book";
    }

    @GetMapping("/books/edit")
    public String showUpdateForm() {
        return "edit-book";
    }
}
