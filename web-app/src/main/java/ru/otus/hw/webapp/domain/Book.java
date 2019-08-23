package ru.otus.hw.webapp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "NAME")
    private String name;
    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "AUTHOR_ID")
    private Author author;
    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "GENRE_ID")
    private Genre genre;
}
