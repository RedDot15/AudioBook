package org.example.audiobook.controller;

import lombok.RequiredArgsConstructor;
import org.example.audiobook.dto.response.FileUploadResponse;
import org.example.audiobook.service.cloudinary.IFileUploadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileUploadController {

    private final IFileUploadService fileUploadService;

    @PostMapping("/cover-image")
    public ResponseEntity<FileUploadResponse> uploadCoverImage(@RequestParam("file") MultipartFile file) {
        try {
            FileUploadResponse response = fileUploadService.uploadCoverImage(file);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new FileUploadResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/audio")
    public ResponseEntity<FileUploadResponse> uploadAudioFile(@RequestParam("file") MultipartFile file) {
        try {
            FileUploadResponse response = fileUploadService.uploadAudioFile(file);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new FileUploadResponse(e.getMessage(), null));
        }
    }
}