package org.example.audiobook.repository;

import org.example.audiobook.entity.FavoriteList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FavoriteListRepository extends JpaRepository<FavoriteList, UUID> {}
