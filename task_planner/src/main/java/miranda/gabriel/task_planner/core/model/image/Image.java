package miranda.gabriel.task_planner.core.model.image;

import java.time.LocalDateTime;

public class Image {

    public Image(Long id, String imageUri, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.imageUri = imageUri;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    private final Long id;

    private String imageUri;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Long getId() {
        return id;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    
}
