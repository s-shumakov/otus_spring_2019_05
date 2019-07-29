package ru.otus.hw.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.hw.domain.Comment;
import ru.otus.hw.domain.Author;
import ru.otus.hw.domain.Book;
import ru.otus.hw.domain.Genre;
import ru.otus.hw.exception.NotFoundException;
import ru.otus.hw.service.LibraryFacadeService;
import ru.otus.hw.service.OutputService;

import java.util.Collections;
import java.util.List;

@ShellComponent
public class LibraryCommands {
    private final LibraryFacadeService libraryFacadeService;
    private final OutputService outputService;

    public LibraryCommands(
            LibraryFacadeService libraryFacadeService,
            OutputService outputService) {
        this.libraryFacadeService = libraryFacadeService;
        this.outputService = outputService;
    }

    @ShellMethod(value = "List all entries in given tables", key = {"l", "list"})
    public void list(@ShellOption String table) {
        switch (table.toLowerCase()) {
            case "authors":
                outputService.printAuthors(libraryFacadeService.findAllAuthors());
                break;
            case "genres":
                outputService.printGenres(libraryFacadeService.findAllGenres());
                break;
            case "books":
                outputService.printBooks(libraryFacadeService.findAllBooks());
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
                    Author author = libraryFacadeService.findAuthorById(id);
                    outputService.printAuthors(Collections.singletonList(author));
                    break;
                case "genres":
                    Genre genre = libraryFacadeService.findGenreById(id);
                    outputService.printGenres(Collections.singletonList(genre));
                    break;
                case "books":
                    Book book = libraryFacadeService.findBookById(id);
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
        Author author = new Author(firstName, lastName);
        libraryFacadeService.saveAuthor(author);
        outputService.println("Author with firstName: " + author.getFirstName() + ", lastName: " + author.getLastName() + " inserted.");
    }

    @ShellMethod(value = "Insert genre", key = {"ig", "insert-genre"})
    public void insertGenre(
            @ShellOption String genreName) {
        Genre genre = new Genre(genreName);
        libraryFacadeService.saveGenre(genre);
        outputService.println("Genre with genreName: " + genre.getGenreName() + " inserted.");
    }

    @ShellMethod(value = "Insert book", key = {"ib", "insert-book"})
    public void insertBook(
            @ShellOption String name,
            @ShellOption Long authorId,
            @ShellOption Long genreId) {
        Author author = libraryFacadeService.findAuthorById(authorId);
        Genre genre = libraryFacadeService.findGenreById(genreId);
        Book book = new Book(name, author, genre);
        libraryFacadeService.saveBook(book);
        outputService.println("Book with name: " + book.getName() + " inserted.");
    }

    @ShellMethod(value = "Add comment to book", key = {"c", "comment"})
    public void addComment(
            @ShellOption String comment,
            @ShellOption Long bookId) {
        Book book = libraryFacadeService.findBookById(bookId);
        Comment commentObj = new Comment(comment, book);
        libraryFacadeService.saveComment(commentObj);
        outputService.println("Comment for book: " + book.getName() + " inserted.");
    }

    @ShellMethod(value = "Get comments for book", key = {"lc", "list-comments"})
    public void listComments(@ShellOption Long bookId) {
        try {
            Book book = libraryFacadeService.findBookById(bookId);
            List<Comment> comments = libraryFacadeService.findCommentsByBook(book);
            outputService.println("Comments for book:");
            outputService.printBooks(Collections.singletonList(book));
            outputService.printComments(comments);
        } catch (NotFoundException e) {
            outputService.println(e.getMessage());
        }

    }
}
