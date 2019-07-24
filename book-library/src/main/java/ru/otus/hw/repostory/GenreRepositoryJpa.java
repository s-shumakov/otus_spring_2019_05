package ru.otus.hw.repostory;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.domain.Genre;
import ru.otus.hw.exception.DataInsertException;
import ru.otus.hw.exception.NotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Transactional
public class GenreRepositoryJpa implements GenreRepository {
    @PersistenceContext
    private final EntityManager em;

    public GenreRepositoryJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public Long count() {
        TypedQuery<Long> query = em.createQuery("select count(g) from Genres g", Long.class);
        return query.getSingleResult();
    }

    @Override
    public List<Genre> findAll() {
        TypedQuery<Genre> query = em.createQuery("select g from Genres g", Genre.class);
        return query.getResultList();
    }

    @Override
    public Genre findById(Long id) {
        Genre genre = em.find(Genre.class, id);
        if (genre == null) {
            throw new NotFoundException("Genre with id " + id + " not found.");
        }
        return genre;
    }

    @Override
    public void insert(Genre genre) {
        try {
            em.persist(genre);
        } catch (PersistenceException e) {
            throw new DataInsertException("Error insert Genre with name " + genre.getGenreName());
        }
    }

}
