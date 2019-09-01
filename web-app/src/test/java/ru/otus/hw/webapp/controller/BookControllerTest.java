package ru.otus.hw.webapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.core.IsNot;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.webapp.domain.Author;
import ru.otus.hw.webapp.domain.Book;
import ru.otus.hw.webapp.domain.Genre;
import ru.otus.hw.webapp.repostory.AuthorRepository;
import ru.otus.hw.webapp.repostory.BookRepository;
import ru.otus.hw.webapp.repostory.GenreRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest
public class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BookRepository bookRepository;
    @MockBean
    private AuthorRepository authorRepository;
    @MockBean
    private GenreRepository genreRepository;
    private List<Book> books;

    @Before
    public void init() {
        Book book1 = new Book(1L, "Book 1",
                new Author(1L, "FirstName 1", "LastName 1"),
                new Genre(1L, "Genre 1"));
        Book book2 = new Book(2L, "Book 2",
                new Author(2L, "FirstName 2", "LastName 2"),
                new Genre(2L, "Genre 2"));
        books = Arrays.asList(book1, book2);
    }

    @Test
    public void listBooks() throws Exception {
        when(bookRepository.findAll()).thenReturn(books);
        this.mockMvc.perform(get("/api/books")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Book 1")))
                .andExpect(content().string(containsString("Book 2")));
    }

    @Test
    public void addBook() throws Exception {
        Book book3 = new Book(3L, "Book 3",
                new Author(3L, "FirstName 3", "LastName 3"),
                new Genre(3L, "Genre 3"));
        when(bookRepository.save(book3)).thenReturn(book3);
        ObjectMapper objectMapper = new ObjectMapper();

        this.mockMvc.perform(post("/api/books").content(objectMapper.writeValueAsString(book3))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Book 3"));
    }

    @Test
    public void updateBook() throws Exception {
        List<Book> bookList = new ArrayList<>(books);
        Book book = bookList.get(0);
        book.setName("New name");
        when(bookRepository.save(book)).thenReturn(book);
        ObjectMapper objectMapper = new ObjectMapper();

        this.mockMvc.perform(put("/api/books/1").content(objectMapper.writeValueAsString(book))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New name"));
    }

    @Test
    public void deleteBook() throws Exception {
        List<Book> bookList = new ArrayList<>(books);
        Book book = bookList.get(0);
        book.setName("New name");
        when(bookRepository.findById(book.getId())).thenReturn(java.util.Optional.of(book));
        doNothing().when(bookRepository).delete(book);
        bookList.remove(0);
        when(bookRepository.findAll()).thenReturn(bookList);

        this.mockMvc.perform(delete("/api/books/1"))
                .andDo(print()).andExpect(status().isOk());
        this.mockMvc.perform(get("/api/books"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(IsNot.not("Book 1")));
    }

    @Test
    public void getBook() throws Exception {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(books.get(0)));
        this.mockMvc.perform(get("/api/books/1")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Book 1"));
    }
}
