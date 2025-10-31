package miranda.gabriel.tenus.application.usecases;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import miranda.gabriel.tenus.core.enums.ImageEntityType;
import miranda.gabriel.tenus.core.model.image.Image;

public interface ImageUsecases {

    public static final List<String> ALLOWED_CONTENT_TYPES = Arrays.asList(
        "image/jpeg", "image/jpg", "image/png", "image/gif", "image/webp"
    );

    public static final long MAX_FILE_SIZE = 5 * 1024 * 1024; //1024b = 1KB

    public Image uploadImage(MultipartFile file, String userId, ImageEntityType entityType);

    public boolean deleteImage(String imageUrl);
}
