package ru.otus.hw.repostory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.otus.hw.domain.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Override
    @Query("select b from Books b join fetch b.author join fetch b.genre")
    List<Book> findAll();
}
