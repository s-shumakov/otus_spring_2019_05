package ru.otus.hw.service;

import org.springframework.stereotype.Service;
import ru.otus.hw.domain.Author;
import ru.otus.hw.domain.Book;
import ru.otus.hw.domain.Comment;
import ru.otus.hw.domain.Genre;
import ru.otus.hw.exception.NotFoundException;
import ru.otus.hw.repostory.AuthorRepository;
import ru.otus.hw.repostory.BookRepository;
import ru.otus.hw.repostory.CommentRepository;
import ru.otus.hw.repostory.GenreRepository;

import java.util.List;

@Service
public class LibraryFacadeServiceImpl implements LibraryFacadeService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final CommentRepository commentRepository;

    public LibraryFacadeServiceImpl(
            AuthorRepository authorRepository,
            BookRepository bookRepository,
            GenreRepository genreRepository,
            CommentRepository commentRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.genreRepository = genreRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public List<Author> findAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public List<Genre> findAllGenres() {
        return genreRepository.findAll();
    }

    @Override
    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Author findAuthorById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Author with id " + id + " not found."));
    }

    @Override
    public Genre findGenreById(Long id) {
        return genreRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Genre with id " + id + " not found."));
    }

    @Override
    public Book findBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book with id " + id + " not found."));
    }

    @Override
    public void saveAuthor(Author author) {
        authorRepository.save(author);
    }

    @Override
    public void saveGenre(Genre genre) {
        genreRepository.save(genre);
    }

    @Override
    public void saveBook(Book book) {
        bookRepository.save(book);
    }

    @Override
    public void saveComment(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public List<Comment> findCommentsByBook(Book book) {
        return commentRepository.findByBook(book);
    }

}
