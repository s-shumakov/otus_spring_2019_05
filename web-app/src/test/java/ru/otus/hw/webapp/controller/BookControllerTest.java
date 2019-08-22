package ru.otus.hw.webapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;
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
        this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
                .andExpect(view().name("list-book"))
                .andExpect(content().string(containsString("Books")))
                .andExpect(content().string(containsString("Book 1")));
    }

    @Test
    public void showAddForm() throws Exception {
        this.mockMvc.perform(get("/book/add")).andDo(print()).andExpect(status().isOk())
                .andExpect(view().name("add-book"))
                .andExpect(content().string(containsString("Add book")));
    }

    @Test
    public void addBook() throws Exception {
        Book book3 = new Book(3L, "Book 3",
                new Author(3L, "FirstName 3", "LastName 3"),
                new Genre(3L, "Genre 3"));
        List<Book> bookList = new ArrayList<>(books);
        bookList.add(book3);
        when(bookRepository.save(book3)).thenReturn(book3);
        when(bookRepository.findAll()).thenReturn(bookList);
        ObjectMapper objectMapper = new ObjectMapper();

        this.mockMvc.perform(post("/book/add").content(objectMapper.writeValueAsString(book3)))
                .andDo(print()).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
        this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Book 3")))
                .andExpect(view().name("list-book"));
    }

    @Test
    public void showUpdateForm() throws Exception {
        List<Book> bookList = new ArrayList<>(books);
        Book book = bookList.get(0);
        when(bookRepository.findById(book.getId())).thenReturn(java.util.Optional.of(book));
        this.mockMvc.perform(get("/book/edit").param("id", "1"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(view().name("edit-book"))
                .andExpect(content().string(containsString("Book info")));
    }

    @Test
    public void updateBook() throws Exception {
        List<Book> bookList = new ArrayList<>(books);
        Book book = bookList.get(0);
        book.setName("New name");
        when(bookRepository.save(book)).thenReturn(book);
        when(bookRepository.findAll()).thenReturn(bookList);
        ObjectMapper objectMapper = new ObjectMapper();

        this.mockMvc.perform(post("/book/edit").content(objectMapper.writeValueAsString(book)))
                .andDo(print()).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
        this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("New name")))
                .andExpect(view().name("list-book"));
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

        this.mockMvc.perform(delete("/book/delete").param("id", "1"))
                .andDo(print()).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
        this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(not(containsString("Book 1"))))
                .andExpect(view().name("list-book"));
    }
}
