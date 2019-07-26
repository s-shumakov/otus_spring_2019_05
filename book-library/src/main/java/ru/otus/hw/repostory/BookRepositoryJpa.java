package ru.otus.hw.repostory;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.domain.Book;
import ru.otus.hw.exception.DataInsertException;
import ru.otus.hw.exception.NotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Transactional
public class BookRepositoryJpa implements BookRepository {
    @PersistenceContext
    private final EntityManager em;

    public BookRepositoryJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public Long count() {
        TypedQuery<Long> query = em.createQuery("select count(b) from Books b", Long.class);
        return query.getSingleResult();
    }

    @Override
    public List<Book> findAll() {
        TypedQuery<Book> query = em.createQuery(
                "select b from Books b join fetch b.author join fetch b.genre", Book.class);
//        TypedQuery<Book> query = em.createQuery("select b from Books b", Book.class);
        return query.getResultList();
    }

    @Override
    public Book findById(Long id) {
        Book book = em.find(Book.class, id);
        if (book == null) {
            throw new NotFoundException("Book with id " + id + " not found.");
        }
        return book;
    }

    @Override
    public void insert(Book book) {
        try {
            em.persist(book);
        } catch (PersistenceException e) {
            throw new DataInsertException("Error insert Book with name: " + book.getName());
        }
    }

}
