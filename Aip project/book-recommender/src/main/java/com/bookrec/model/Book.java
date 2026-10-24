package com.bookrec.model;

import jakarta.persistence.*;

@Entity
@Table(name = "books")
public class Book {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false) private String title;
    @Column(nullable = false) private String author;
    @Column(nullable = false) private String genre;
    private Integer publishedYear;
    @Column(length = 1000) private String description;

    public Book() {}
    public Book(String title, String author, String genre, Integer publishedYear, String description) {
        this.title = title; this.author = author; this.genre = genre;
        this.publishedYear = publishedYear; this.description = description;
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    public Integer getPublishedYear() { return publishedYear; }
    public void setPublishedYear(Integer y) { this.publishedYear = y; }
    public String getDescription() { return description; }
    public void setDescription(String d) { this.description = d; }
}
