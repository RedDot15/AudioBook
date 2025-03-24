package org.example.audiobook.repository;

import org.example.audiobook.entity.AudioBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AudioBookRepository extends JpaRepository<AudioBook, UUID> {
}
