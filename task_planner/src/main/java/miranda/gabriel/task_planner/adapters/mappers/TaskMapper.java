package miranda.gabriel.task_planner.adapters.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import miranda.gabriel.task_planner.adapters.outbounds.entities.JpaTaskEntity;
import miranda.gabriel.task_planner.core.model.task.Task;

@Mapper(componentModel = "spring", uses = {ImageMapper.class, AddressMapper.class})
public interface TaskMapper {

    @Mapping(target = "board", ignore = true)
    @Mapping(target = "taskLogs", ignore = true)
    Task toDomain(JpaTaskEntity entity);

    @Mapping(target = "board", ignore = true)
    @Mapping(target = "taskLogs", ignore = true)
    JpaTaskEntity toEntity(Task domain);

    List<Task> toDomainList(List<JpaTaskEntity> entities);

    List<JpaTaskEntity> toEntityList(List<Task> domains);
}
