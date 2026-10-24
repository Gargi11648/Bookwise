package com.bookrec.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ratings", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "book_id"}))
public class Rating {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "user_id")
    private AppUser user;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "book_id")
    private Book book;
    @Column(nullable = false) private Integer score; // 1..5

    public Rating() {}
    public Rating(AppUser user, Book book, Integer score) { this.user = user; this.book = book; this.score = score; }
    public Long getId() { return id; }
    public AppUser getUser() { return user; }
    public void setUser(AppUser user) { this.user = user; }
    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }
    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }
}
