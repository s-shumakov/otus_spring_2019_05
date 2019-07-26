package ru.otus.hw.domain;

import javax.persistence.*;

@Entity(name = "Comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String comment;
    @ManyToOne
    private Book book;

    public Comment() {
    }

    public Comment(String comment, Book book) {
        this.comment = comment;
        this.book = book;
    }

    public Comment(Long id, String comment) {
        this.id = id;
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
