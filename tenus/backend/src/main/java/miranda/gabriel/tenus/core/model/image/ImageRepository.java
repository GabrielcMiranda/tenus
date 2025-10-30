package miranda.gabriel.tenus.core.model.image;

import java.util.Optional;

public interface ImageRepository {

    Image save(Image image);
    
    Optional<Image> findByImageUri(String imageUri);
    
    void delete(Image image);
}
