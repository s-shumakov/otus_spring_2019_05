package ru.otus.hw.webapp.repostory;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw.webapp.domain.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
