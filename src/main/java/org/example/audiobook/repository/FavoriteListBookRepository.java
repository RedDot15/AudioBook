package org.example.audiobook.repository;

import org.example.audiobook.entity.FavoriteListBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FavoriteListBookRepository extends JpaRepository<FavoriteListBook, UUID> {}
