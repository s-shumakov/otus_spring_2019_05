package ru.otus.hw.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.otus.hw.webapp.domain.Book;
import ru.otus.hw.webapp.repostory.AuthorRepository;
import ru.otus.hw.webapp.repostory.BookRepository;
import ru.otus.hw.webapp.repostory.GenreRepository;

import java.util.List;

@Controller
public class BookController {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    public BookController(BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }

    @GetMapping("/")
    public String listBooks(Model model) {
        List<Book> books = bookRepository.findAll();
        model.addAttribute("books", books);
        return "list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        Book book = new Book();
        model.addAttribute("book", book);
        model.addAttribute("authors", authorRepository.findAll());
        model.addAttribute("genres", genreRepository.findAll());
        return "add";
    }

    @PostMapping("/add")
    public String addBook(@ModelAttribute("book") Book book, BindingResult result, ModelMap model) {
        if (result.hasErrors()) {
            return "add";
        }
        bookRepository.save(book);
        return "redirect:/";
    }

    @GetMapping("/edit")
    public String showUpdateForm(@RequestParam("id") long id, Model model) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Book with Id: " + id + " not found"));
        model.addAttribute("book", book);
        model.addAttribute("authors", authorRepository.findAll());
        model.addAttribute("genres", genreRepository.findAll());
        return "edit";
    }

    @PostMapping("/edit")
    public String updateBook(@ModelAttribute("book") Book book, BindingResult result, ModelMap model) {
        if (result.hasErrors()) {
            return "edit";
        }
        bookRepository.save(book);
        return "redirect:/";
    }

    @GetMapping("/delete")
    public String deleteBook(@RequestParam("id") long id, Model model) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        bookRepository.delete(book);
        return "redirect:/";
    }
}
