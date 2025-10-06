package miranda.gabriel.tenus.adapters.mappers;

import org.mapstruct.Mapper;

import miranda.gabriel.tenus.adapters.outbounds.entities.JpaImageEntity;
import miranda.gabriel.tenus.core.model.image.Image;

@Mapper(componentModel = "spring")
public interface ImageMapper {

    Image toDomain(JpaImageEntity entity);

    JpaImageEntity toEntity(Image domain);
}
