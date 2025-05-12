package org.example.audiobook.repository;

import org.example.audiobook.entity.FavoriteCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface FavoriteCategoryRepository extends JpaRepository<FavoriteCategory, UUID> {
    // Lấy danh sách FavoriteCategory theo userId với phân trang
    Page<FavoriteCategory> findByUserId(UUID userId, Pageable pageable);

    // Lấy danh sách categoryId từ FavoriteCategory theo userId
    @Query("SELECT fc.category.id FROM FavoriteCategory fc WHERE fc.user.id = :userId")
    List<UUID> findCategoryIdsByUserId(UUID userId);

    boolean existsByUserId(UUID userId);

}