// AudioBookController.java
package org.example.audiobook.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.audiobook.dto.response.BookChapterResponse;
import org.example.audiobook.helper.ResponseObject;
import org.example.audiobook.service.BookChapterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.example.audiobook.helper.ResponseBuilder.buildResponse;

@RestController
@RequestMapping("/api/book-chapter")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookChapterController {

    BookChapterService bookChapterService;

    @GetMapping("/audio-book/{audioBookId}")
    public ResponseEntity<ResponseObject> getByAudioBookId(
            @PathVariable UUID audioBookId) {
        List<BookChapterResponse> response = bookChapterService.getAllByAudioBookId(audioBookId);
        return buildResponse(HttpStatus.OK, "Get book chapter successfully.", response);
    }

}