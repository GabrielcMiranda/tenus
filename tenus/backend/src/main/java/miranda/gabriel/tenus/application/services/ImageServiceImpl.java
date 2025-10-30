package miranda.gabriel.tenus.application.services;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import miranda.gabriel.tenus.adapters.outbounds.cloudstorage.CloudStoragePort;
import miranda.gabriel.tenus.application.usecases.ImageUsecases;
import miranda.gabriel.tenus.core.model.image.Image;
import miranda.gabriel.tenus.core.model.image.ImageRepository;
import miranda.gabriel.tenus.infrastructure.exception.TenusExceptions;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageUsecases{
    
    private final CloudStoragePort cloudStoragePort;
    private final ImageRepository imageRepository;
    
    public Image uploadImage(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            return null;
        }
        
        validateImage(file);
        
        try {
            String imageUrl = cloudStoragePort.uploadFile(
                file.getOriginalFilename(),
                file.getInputStream(),
                file.getContentType(),
                file.getSize()
            );
            
            log.info("Image uploaded successfully: {}", imageUrl);
            
            var image = new Image();
            image.setImageUri(imageUrl);
            image.setCreatedAt(LocalDateTime.now());
            image.setUpdatedAt(LocalDateTime.now());
            
            var savedImage = imageRepository.save(image);
            return savedImage;

        } catch (IOException e) {
            log.error("Error reading image file: {}", e.getMessage(), e);
            throw new TenusExceptions.BusinessRuleViolationException("Error processing image file");
        }
    }
    
    public boolean deleteImage(String imageUri) {
        if (imageUri == null || imageUri.trim().isEmpty()) {
            return true;
        }
        
        try {
            // Buscar imagem no banco de dados
            var imageOptional = imageRepository.findByImageUri(imageUri);
            
            if (imageOptional.isEmpty()) {
                log.warn("Image not found in database: {}", imageUri);
                // Tenta deletar do storage mesmo que não esteja no banco
                return cloudStoragePort.deleteFile(imageUri);
            }
            
            // Deletar do storage primeiro
            boolean deletedFromStorage = cloudStoragePort.deleteFile(imageUri);
            
            if (deletedFromStorage) {
                // Se deletou do storage, deleta do banco
                imageRepository.delete(imageOptional.get());
                log.info("Image deleted successfully from storage and database: {}", imageUri);
                return true;
            } else {
                log.error("Failed to delete image from storage: {}", imageUri);
                return false;
            }
            
        } catch (Exception e) {
            log.error("Error deleting image: {}", e.getMessage(), e);
            throw new TenusExceptions.BusinessRuleViolationException("Error deleting image");
        }
    }
    
    private void validateImage(MultipartFile file) {
      
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new TenusExceptions.BusinessRuleViolationException(
                String.format("Image size must be less than %d MB", MAX_FILE_SIZE / (1024 * 1024))
            );
        }
        
        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_CONTENT_TYPES.contains(contentType.toLowerCase())) {
            throw new TenusExceptions.BusinessRuleViolationException(
                "Invalid image format. Allowed formats: " + String.join(", ", ALLOWED_CONTENT_TYPES)
            );
        }
        
        String fileName = file.getOriginalFilename();
        if (fileName == null || fileName.trim().isEmpty()) {
            throw new TenusExceptions.BusinessRuleViolationException("Image file name cannot be empty");
        }
        
        String fileExtension = getFileExtension(fileName);
        List<String> allowedExtensions = Arrays.asList("jpg", "jpeg", "png", "gif", "webp");
        if (!allowedExtensions.contains(fileExtension.toLowerCase())) {
            throw new TenusExceptions.BusinessRuleViolationException(
                "Invalid image extension. Allowed extensions: " + String.join(", ", allowedExtensions)
            );
        }
        
        log.info("Image validation passed: {} ({})", fileName, contentType);
    }
    
    private String getFileExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}