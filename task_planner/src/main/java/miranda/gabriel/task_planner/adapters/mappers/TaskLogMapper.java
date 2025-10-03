package miranda.gabriel.task_planner.adapters.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import miranda.gabriel.task_planner.adapters.outbounds.entities.JpaTaskLogEntity;
import miranda.gabriel.task_planner.core.model.task_log.TaskLog;

@Mapper(componentModel = "spring", uses = {ImageMapper.class, AddressMapper.class})
public interface TaskLogMapper {

    @Mapping(target = "task", ignore = true)
    TaskLog toDomain(JpaTaskLogEntity entity);

    @Mapping(target = "task", ignore = true)
    JpaTaskLogEntity toEntity(TaskLog domain);

    List<TaskLog> toDomainList(List<JpaTaskLogEntity> entities);

    List<JpaTaskLogEntity> toEntityList(List<TaskLog> domains);
}
