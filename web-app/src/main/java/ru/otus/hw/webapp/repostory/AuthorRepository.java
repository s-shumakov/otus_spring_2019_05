package ru.otus.hw.webapp.repostory;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw.webapp.domain.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {

}
