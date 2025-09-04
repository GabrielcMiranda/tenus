package miranda.gabriel.task_planner.utils.mappers;

import org.mapstruct.Mapper;

import miranda.gabriel.task_planner.adapters.outbounds.entities.JpaUserEntity;
import miranda.gabriel.task_planner.core.model.user.User;

@Mapper(componentModel = "spring", uses = {ActivityBoardMapper.class})
public interface UserMapper {

    User toDomain(JpaUserEntity entity);

    JpaUserEntity toEntity(User domain);
}
