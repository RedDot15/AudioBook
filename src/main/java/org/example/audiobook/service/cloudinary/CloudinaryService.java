package org.example.audiobook.service.cloudinary;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.example.audiobook.dto.response.FileUploadResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryService implements IFileUploadService {
    public static final String AUDIO_BOOK_FOLDER = "audio_book";
    private final Cloudinary cloudinary;


    @Override
    public FileUploadResponse uploadFile(MultipartFile file) throws Exception {
        Map<?,?> uploadResult = cloudinary.uploader().upload(file.getBytes(),
                ObjectUtils.asMap(
                        "folder", AUDIO_BOOK_FOLDER
                ));
        FileUploadResponse response = new FileUploadResponse();
        response.setFilePath((String) uploadResult.get("url"));
        response.setDateTime(LocalDateTime.now());
        return response;
    }
}
