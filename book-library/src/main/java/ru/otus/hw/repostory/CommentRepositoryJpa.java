package ru.otus.hw.repostory;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.domain.Book;
import ru.otus.hw.domain.Comment;
import ru.otus.hw.exception.DataInsertException;
import ru.otus.hw.exception.NotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Transactional
public class CommentRepositoryJpa implements CommentRepository {
    @PersistenceContext
    private final EntityManager em;

    public CommentRepositoryJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public Long count() {
        TypedQuery<Long> query = em.createQuery("select count(c) from Comments c", Long.class);
        return query.getSingleResult();
    }

    @Override
    public List<Comment> findAll() {
        TypedQuery<Comment> query = em.createQuery("select c from Comments c join fetch c.book", Comment.class);
        return query.getResultList();
    }

    @Override
    public Comment findById(Long id) {
        Comment comment = em.find(Comment.class, id);
        if (comment == null) {
            throw new NotFoundException("Comment with id " + id + " not found.");
        }
        return comment;
    }

    @Override
    public List<Comment> findByBook(Book book) {
        TypedQuery<Comment> query = em.createQuery(
                "select c from Comments c " +
                        "join fetch c.book b " +
                        "join fetch b.author join fetch b.genre " +
                        "where c.book = :book ", Comment.class);
        query.setParameter("book", book);
        return query.getResultList();
    }

    @Override
    public void insert(Comment comment) {
        try {
            em.persist(comment);
        } catch (PersistenceException e) {
            throw new DataInsertException("Error insert Comment " + comment.getComment() +
                    " for book " + comment.getBook().getName());
        }
    }
}
