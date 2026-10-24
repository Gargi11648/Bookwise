package com.bookrec.controller;

import com.bookrec.dao.RecommendationJdbcDao;
import com.bookrec.model.*;
import com.bookrec.repository.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.SQLException;
import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiController {
    private final BookRepository books; private final UserRepository users;
    private final RatingRepository ratings; private final RecommendationJdbcDao jdbc;

    public ApiController(BookRepository b, UserRepository u, RatingRepository r, RecommendationJdbcDao j) {
        books = b; users = u; ratings = r; jdbc = j;
    }

    // ---- Hibernate-backed endpoints ----
    @GetMapping("/books")
    public List<Book> allBooks(@RequestParam(required = false) String q) {
        if (q == null || q.isBlank()) return books.findAll();
        return books.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrGenreContainingIgnoreCase(q, q, q);
    }

    @PostMapping("/books")
    public Book addBook(@RequestBody Book book) { book.setId(null); return books.save(book); }

    @GetMapping("/users")
    public List<AppUser> allUsers() { return users.findAll(); }

    @PostMapping("/users")
    public ResponseEntity<?> addUser(@RequestBody AppUser user) {
        if (user.getUsername() == null || user.getUsername().isBlank())
            return ResponseEntity.badRequest().body(Map.of("error", "username required"));
        return ResponseEntity.ok(users.findByUsername(user.getUsername()).orElseGet(() -> users.save(new AppUser(user.getUsername()))));
    }

    public record RatingRequest(Long userId, Long bookId, Integer score) {}

    @PostMapping("/ratings")
    public ResponseEntity<?> rate(@RequestBody RatingRequest req) {
        if (req.score() == null || req.score() < 1 || req.score() > 5)
            return ResponseEntity.badRequest().body(Map.of("error", "score must be 1-5"));
        Optional<AppUser> u = users.findById(req.userId());
        Optional<Book> b = books.findById(req.bookId());
        if (u.isEmpty() || b.isEmpty()) return ResponseEntity.badRequest().body(Map.of("error", "unknown user or book"));
        Rating r = ratings.findByUserIdAndBookId(req.userId(), req.bookId()).orElseGet(() -> new Rating(u.get(), b.get(), 0));
        r.setScore(req.score());
        ratings.save(r);
        return ResponseEntity.ok(Map.of("status", "saved"));
    }

    // ---- Plain-JDBC endpoints ----
    @GetMapping("/recommendations/{userId}")
    public List<Map<String, Object>> recommend(@PathVariable long userId) throws SQLException {
        return jdbc.recommendFor(userId);
    }

    @GetMapping("/db-status")
    public Map<String, Object> dbStatus() throws SQLException { return jdbc.connectionInfo(); }
}
