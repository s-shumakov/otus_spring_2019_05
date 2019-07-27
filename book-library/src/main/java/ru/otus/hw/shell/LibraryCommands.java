package ru.otus.hw.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.hw.domain.Comment;
import ru.otus.hw.repostory.AuthorRepository;
import ru.otus.hw.repostory.BookRepository;
import ru.otus.hw.repostory.CommentRepository;
import ru.otus.hw.repostory.GenreRepository;
import ru.otus.hw.domain.Author;
import ru.otus.hw.domain.Book;
import ru.otus.hw.domain.Genre;
import ru.otus.hw.exception.NotFoundException;
import ru.otus.hw.service.OutputService;

import java.util.Collections;
import java.util.List;

@ShellComponent
public class LibraryCommands {
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;
    private final OutputService outputService;

    public LibraryCommands(
            AuthorRepository authorRepository,
            GenreRepository genreRepository,
            BookRepository bookRepository,
            CommentRepository commentRepository,
            OutputService outputService) {
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.bookRepository = bookRepository;
        this.commentRepository = commentRepository;
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
                    Author author = authorRepository.findById(id)
                            .orElseThrow(() -> new NotFoundException("Author with id " + id + " not found."));
                    outputService.printAuthors(Collections.singletonList(author));
                    break;
                case "genres":
                    Genre genre = genreRepository.findById(id)
                            .orElseThrow(() -> new NotFoundException("Genre with id " + id + " not found."));
                    outputService.printGenres(Collections.singletonList(genre));
                    break;
                case "books":
                    Book book = bookRepository.findById(id)
                            .orElseThrow(() -> new NotFoundException("Book with id " + id + " not found."));
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
        authorRepository.save(author);
        outputService.println("Author with firstName: " + author.getFirstName() + ", lastName: " + author.getLastName() + " inserted.");
    }

    @ShellMethod(value = "Insert genre", key = {"ig", "insert-genre"})
    public void insertGenre(
            @ShellOption String genreName) {
        Genre genre = new Genre(genreName);
        genreRepository.save(genre);
        outputService.println("Genre with genreName: " + genre.getGenreName() + " inserted.");
    }

    @ShellMethod(value = "Insert book", key = {"ib", "insert-book"})
    public void insertBook(
            @ShellOption String name,
            @ShellOption Long authorId,
            @ShellOption Long genreId) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new NotFoundException("Author with id " + authorId + " not found."));
        Genre genre = genreRepository.findById(genreId)
                .orElseThrow(() -> new NotFoundException("Genre with id " + genreId + " not found."));
        Book book = new Book(name, author, genre);
        bookRepository.save(book);
        outputService.println("Book with name: " + book.getName() + " inserted.");
    }

    @ShellMethod(value = "Add comment to book", key = {"c", "comment"})
    public void addComment(
            @ShellOption String comment,
            @ShellOption Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException("Book with id " + bookId + " not found."));
        Comment commentObj = new Comment(comment, book);
        commentRepository.save(commentObj);
        outputService.println("Comment for book: " + book.getName() + " inserted.");
    }

    @ShellMethod(value = "Get comments for book", key = {"lc", "list-comments"})
    public void listComments(@ShellOption Long bookId) {
        try {
            Book book = bookRepository.findById(bookId)
                    .orElseThrow(() -> new NotFoundException("Book with id " + bookId + " not found."));;
            List<Comment> comments = commentRepository.findByBook(book);
            outputService.println("Comments for book:");
            outputService.printBooks(Collections.singletonList(book));
            outputService.printComments(comments);
        } catch (NotFoundException e) {
            outputService.println(e.getMessage());
        }

    }
}
