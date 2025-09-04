package miranda.gabriel.task_planner.utils.mappers;

import org.mapstruct.Mapper;

import miranda.gabriel.task_planner.adapters.outbounds.entities.JpaTaskLogEntity;
import miranda.gabriel.task_planner.core.model.task_log.TaskLog;

@Mapper(componentModel = "spring", uses = {TaskMapper.class, ImageMapper.class, AddressMapper.class})
public interface TaskLogMapper {

    TaskLog toDomain(JpaTaskLogEntity entity);

    JpaTaskLogEntity toEntity(TaskLog domain);
}
