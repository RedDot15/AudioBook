package org.example.audiobook.service.cloudinary;

import org.example.audiobook.dto.response.FileUploadResponse;
import org.springframework.web.multipart.MultipartFile;

public interface IFileUploadService {

    FileUploadResponse uploadFile(MultipartFile file) throws Exception;
}
