package miranda.gabriel.task_planner.adapters.mappers;

import org.mapstruct.Mapper;

import miranda.gabriel.task_planner.adapters.outbounds.entities.JpaImageEntity;
import miranda.gabriel.task_planner.core.model.image.Image;

@Mapper(componentModel = "spring")
public interface ImageMapper {

    Image toDomain(JpaImageEntity entity);

    JpaImageEntity toEntity(Image domain);
}
