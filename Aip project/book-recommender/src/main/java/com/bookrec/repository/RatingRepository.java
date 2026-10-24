package com.bookrec.repository;

import com.bookrec.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    Optional<Rating> findByUserIdAndBookId(Long userId, Long bookId);
}
