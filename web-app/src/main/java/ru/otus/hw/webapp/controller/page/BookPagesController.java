package ru.otus.hw.webapp.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class BookPagesController {
    @GetMapping("/")
    public String listBooks() {
        return "list-book";
    }

    @GetMapping("/book/add")
    public String showAddForm() {
        return "add-book";
    }

    @GetMapping("/book/edit")
    public String showUpdateForm() {
        return "edit-book";
    }
}
