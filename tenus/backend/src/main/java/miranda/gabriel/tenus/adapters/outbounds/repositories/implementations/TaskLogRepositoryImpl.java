package miranda.gabriel.tenus.adapters.outbounds.repositories.implementations;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import miranda.gabriel.tenus.adapters.mappers.MapperService;
import miranda.gabriel.tenus.adapters.outbounds.repositories.JpaTaskLogRepository;
import miranda.gabriel.tenus.core.model.task_log.TaskLog;
import miranda.gabriel.tenus.core.model.task_log.TaskLogRepository;

@Repository
@RequiredArgsConstructor
public class TaskLogRepositoryImpl implements TaskLogRepository{

    private final JpaTaskLogRepository jpaTaskLogRepository;
    private final MapperService mapperService;

    @Override
    public TaskLog save(TaskLog tasklog){
        var entity = mapperService.taskLogToEntity(tasklog);
        var savedEntity = jpaTaskLogRepository.save(entity);
        return mapperService.taskLogToDomain(savedEntity);
    }
}
