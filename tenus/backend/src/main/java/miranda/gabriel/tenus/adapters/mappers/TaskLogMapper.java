package miranda.gabriel.tenus.adapters.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import miranda.gabriel.tenus.adapters.outbounds.entities.JpaTaskLogEntity;
import miranda.gabriel.tenus.core.model.task_log.TaskLog;

@Mapper(componentModel = "spring", uses = {ImageMapper.class})
public interface TaskLogMapper {

    @Mapping(target = "task", ignore = true)
    TaskLog toDomain(JpaTaskLogEntity entity);

    @Mapping(target = "task", ignore = true)
    JpaTaskLogEntity toEntity(TaskLog domain);

    List<TaskLog> toDomainList(List<JpaTaskLogEntity> entities);

    List<JpaTaskLogEntity> toEntityList(List<TaskLog> domains);
}
