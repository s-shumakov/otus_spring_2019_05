package ru.otus.hw.repostory;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw.domain.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
