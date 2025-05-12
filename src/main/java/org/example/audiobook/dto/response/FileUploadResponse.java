package org.example.audiobook.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileUploadResponse {
    private String filePath; // URL của file được upload
    private LocalDateTime dateTime; // Thời gian upload
}