package org.example.audiobook.repository;

import org.example.audiobook.entity.AudioBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AudioBookRepository extends JpaRepository<AudioBook, UUID> {

    @Query("SELECT a FROM AudioBook a WHERE LOWER(a.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    List<AudioBook> findByTitleContainingIgnoreCase(@Param("title") String title);

    Page<AudioBook> findByCategoryId(UUID categoryId, Pageable pageable);

    Page<AudioBook> findByUserId(UUID userId, Pageable pageable);

    @Query("SELECT ab FROM AudioBook ab WHERE ab.category.id IN :categoryIds")
    Page<AudioBook> findByCategoryIds(List<UUID> categoryIds, Pageable pageable);

    @Query("SELECT a FROM AudioBook a WHERE LOWER(a.title) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(a.author) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<AudioBook> findBySearch(@Param("search") String search, Pageable pageable);

    @Query("SELECT a FROM AudioBook a WHERE " +
            "(LOWER(a.title) LIKE LOWER(CONCAT('%', :textsearch, '%')) OR " +
            "LOWER(a.author) LIKE LOWER(CONCAT('%', :textsearch, '%'))) AND " +
            "a.user.id = :userId")
    Page<AudioBook> findByTextsearchAndUserId(
            @Param("textsearch") String textsearch,
            @Param("userId") UUID userId,
            Pageable pageable
    );

    Page<AudioBook> findAllByOrderByPublishedYearDesc(Pageable pageable);
}