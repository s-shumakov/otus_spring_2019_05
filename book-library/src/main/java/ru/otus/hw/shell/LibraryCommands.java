package ru.otus.hw.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.hw.repostory.AuthorRepository;
import ru.otus.hw.repostory.BookRepository;
import ru.otus.hw.repostory.GenreRepository;
import ru.otus.hw.domain.Author;
import ru.otus.hw.domain.Book;
import ru.otus.hw.domain.Genre;
import ru.otus.hw.exception.DataInsertException;
import ru.otus.hw.exception.NotFoundException;
import ru.otus.hw.service.OutputService;

import java.util.Collections;

@ShellComponent
public class LibraryCommands {
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;
    private final OutputService outputService;

    public LibraryCommands(AuthorRepository authorRepository, GenreRepository genreRepository, BookRepository bookRepository, OutputService outputService) {
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.bookRepository = bookRepository;
        this.outputService = outputService;
    }

    @ShellMethod(value = "List all entries in given tables", key = {"l", "list"})
    public void list(@ShellOption String table) {
        switch (table.toLowerCase()) {
            case "authors":
                outputService.printAuthors(authorRepository.findAll());
                break;
            case "genres":
                outputService.printGenres(genreRepository.findAll());
                break;
            case "books":
                outputService.printBooks(bookRepository.findAll());
                break;
            default:
                outputService.println("Value '" + table + "' not available, use 'authors', 'genres', 'books'");
                break;
        }
    }

    @ShellMethod(value = "Find entry in given table by id", key = {"f", "find"})
    public void find(
            @ShellOption String table,
            @ShellOption Long id) {
        try {
            switch (table.toLowerCase()) {
                case "authors":
                    Author author = authorRepository.findById(id);
                    outputService.printAuthors(Collections.singletonList(author));
                    break;
                case "genres":
                    Genre genre = genreRepository.findById(id);
                    outputService.printGenres(Collections.singletonList(genre));
                    break;
                case "books":
                    Book book = bookRepository.findById(id);
                    outputService.printBooks(Collections.singletonList(book));
                    break;
                default:
                    outputService.println("Value '" + table + "' not available, use 'authors', 'genres', 'books'");
                    break;
            }
        } catch (NotFoundException e) {
            outputService.println(e.getMessage());
        }
    }

    @ShellMethod(value = "Insert author", key = {"ia", "insert-author"})
    public void insertAuthor(
            @ShellOption String firstName,
            @ShellOption String lastName) {
        try {
            Author author = new Author(firstName, lastName);
            authorRepository.insert(author);
            outputService.println("Author with firstName: " + author.getFirstName() + ", lastName: " + author.getLastName() + " inserted.");
        } catch (DataInsertException e) {
            outputService.println(e.getMessage());
        }
    }

    @ShellMethod(value = "Insert genre", key = {"ig", "insert-genre"})
    public void insertGenre(
            @ShellOption String genreName) {
        try {
            Genre genre = new Genre(genreName);
            genreRepository.insert(genre);
            outputService.println("Genre with genreName: " + genre.getGenreName() + " inserted.");
        } catch (DataInsertException e) {
            outputService.println(e.getMessage());
        }
    }

    @ShellMethod(value = "Insert book", key = {"ib", "insert-book"})
    public void insertBook(
            @ShellOption String name,
            @ShellOption Long authorId,
            @ShellOption Long genreId) {
        try {
            Author author = authorRepository.findById(authorId);
            Genre genre = genreRepository.findById(genreId);
            Book book = new Book(name, author, genre);
            bookRepository.insert(book);
            outputService.println("Book with name: " + book.getName()+ " inserted.");
        } catch (DataInsertException e) {
            outputService.println(e.getMessage());
        }
    }
}
