package ru.otus.hw.webapp.controller.page;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.webapp.repostory.AuthorRepository;
import ru.otus.hw.webapp.repostory.BookRepository;
import ru.otus.hw.webapp.repostory.GenreRepository;
import ru.otus.hw.webapp.security.DomainUserDetailsService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest
@WithMockUser
public class BookPagesControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BookRepository bookRepository;
    @MockBean
    private AuthorRepository authorRepository;
    @MockBean
    private GenreRepository genreRepository;
    @MockBean
    private UserDetailsService userDetailsService;

    @Test
    public void listBooks() throws Exception {
        this.mockMvc.perform(get("/books")).andDo(print()).andExpect(status().isOk())
                .andExpect(view().name("list-book"));
    }

    @Test
    public void showAddForm() throws Exception {
        this.mockMvc.perform(get("/books/add")).andDo(print()).andExpect(status().isOk())
                .andExpect(view().name("add-book"));
    }

    @Test
    public void showUpdateForm() throws Exception {
        this.mockMvc.perform(get("/books/edit")).andDo(print()).andExpect(status().isOk())
                .andExpect(view().name("edit-book"));
    }
}
