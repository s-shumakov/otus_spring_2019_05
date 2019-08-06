package ru.otus.hw.repostory;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw.domain.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {

}
