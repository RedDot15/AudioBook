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
    public static final String COVER_IMAGE_SUBFOLDER = AUDIO_BOOK_FOLDER + "/cover_images";
    public static final String AUDIO_SUBFOLDER = AUDIO_BOOK_FOLDER + "/audio_files";
    private final Cloudinary cloudinary;

    @Override
    public FileUploadResponse uploadFile(MultipartFile file) throws Exception {
        // Mặc định upload như file thông thường
        return uploadFile(file, COVER_IMAGE_SUBFOLDER, "auto");
    }

    public FileUploadResponse uploadCoverImage(MultipartFile file) throws Exception {
        // Upload ảnh bìa với resource_type là image
        return uploadFile(file, COVER_IMAGE_SUBFOLDER, "image");
    }

    public FileUploadResponse uploadAudioFile(MultipartFile file) throws Exception {
        // Upload file âm thanh với resource_type là video (Cloudinary dùng video cho audio)
        return uploadFile(file, AUDIO_SUBFOLDER, "video");
    }

    private FileUploadResponse uploadFile(MultipartFile file, String folder, String resourceType) throws Exception {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(),
                ObjectUtils.asMap(
                        "folder", folder,
                        "resource_type", resourceType
                ));

        FileUploadResponse response = new FileUploadResponse();
        response.setFilePath((String) uploadResult.get("url"));
        response.setDateTime(LocalDateTime.now());
        return response;
    }
}