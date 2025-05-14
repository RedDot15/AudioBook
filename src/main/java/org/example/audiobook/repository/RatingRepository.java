package org.example.audiobook.repository;

import org.example.audiobook.entity.Rating;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RatingRepository extends JpaRepository<Rating, UUID> {
    @Query("SELECT r FROM Rating r WHERE (:audioBookId IS NULL OR r.audioBook.id = :audioBookId)")
    Page<Rating> findAll(Pageable pageable, @Param("audioBookId") UUID audioBookId);
}
