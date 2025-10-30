package miranda.gabriel.tenus.adapters.outbounds.repositories.implementations;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import miranda.gabriel.tenus.adapters.mappers.ImageMapper;
import miranda.gabriel.tenus.adapters.outbounds.repositories.JpaImageRepository;
import miranda.gabriel.tenus.core.model.image.Image;
import miranda.gabriel.tenus.core.model.image.ImageRepository;

@Repository
@RequiredArgsConstructor
public class ImageRepositoryImpl implements ImageRepository{

    private final JpaImageRepository jpaImageRepository;
    private final ImageMapper imageMapper;

    @Override
    public Image save(Image image) {
        var entity = imageMapper.toEntity(image);
        var savedEntity = jpaImageRepository.save(entity);
        return imageMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Image> findByImageUri(String imageUri) {
        return jpaImageRepository.findByImageUri(imageUri)
            .map(imageMapper::toDomain);
    }

    @Override
    public void delete(Image image) {
        var entity = imageMapper.toEntity(image);
        jpaImageRepository.delete(entity);
    }
}
