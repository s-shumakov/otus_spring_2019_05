package ru.otus.hw.repostory;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.domain.Author;
import ru.otus.hw.exception.DataInsertException;
import ru.otus.hw.exception.NotFoundException;

import javax.persistence.*;
import java.util.List;

@Repository
@Transactional
public class AuthorRepositoryJpa implements AuthorRepository {
    @PersistenceContext
    private final EntityManager em;

    public AuthorRepositoryJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public Long count() {
        TypedQuery<Long> query = em.createQuery("select count(a) from Authors a", Long.class);
        return query.getSingleResult();
    }

    @Override
    public List<Author> findAll() {
        TypedQuery<Author> query = em.createQuery("select a from Authors a", Author.class);
        return query.getResultList();
    }

    @Override
    public Author findById(Long id) {
        Author author = em.find(Author.class, id);
        if (author == null) {
            throw new NotFoundException("Author with id " + id + " not found.");
        }
        return author;
    }

    @Override
    public void insert(Author author) {
        try {
            em.persist(author);
        } catch (PersistenceException e) {
            throw new DataInsertException(
                    "Error insert Author with firstName: " + author.getFirstName() + ", lastName: " + author.getLastName()
            );
        }
    }

}
