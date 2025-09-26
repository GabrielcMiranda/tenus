package miranda.gabriel.task_planner.adapters.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import miranda.gabriel.task_planner.adapters.outbounds.entities.JpaUserEntity;
import miranda.gabriel.task_planner.core.model.user.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "boards", ignore = true)
    User toDomain(JpaUserEntity entity);

    @Mapping(target = "boards", ignore = true)
    JpaUserEntity toEntity(User domain);
}
