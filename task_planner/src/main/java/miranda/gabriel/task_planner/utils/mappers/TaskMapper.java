package miranda.gabriel.task_planner.utils.mappers;

import org.mapstruct.Mapper;

import miranda.gabriel.task_planner.adapters.outbounds.entities.JpaTaskEntity;
import miranda.gabriel.task_planner.core.model.task.Task;

@Mapper(componentModel = "spring", uses = {ActivityBoardMapper.class, ImageMapper.class, AddressMapper.class, TaskLogMapper.class})
public interface TaskMapper {

    Task toDomain(JpaTaskEntity entity);

    JpaTaskEntity toEntity(Task domain);
}
