package org.example.audiobook.repository;

import org.example.audiobook.entity.AudioBook;
import org.example.audiobook.entity.BookChapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookChapterRepository extends JpaRepository<BookChapter, UUID> {

    List<BookChapter> findAllByAudioBook(AudioBook audioBook);

    void deleteByAudioBookId(UUID id);
}
