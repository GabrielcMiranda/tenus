package miranda.gabriel.tenus.adapters.outbounds.repositories.implementations;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import miranda.gabriel.tenus.adapters.mappers.MapperService;
import miranda.gabriel.tenus.adapters.outbounds.repositories.JpaTaskRepository;
import miranda.gabriel.tenus.core.model.task.Task;
import miranda.gabriel.tenus.core.model.task.TaskRepository;

@Repository
@RequiredArgsConstructor
public class TaskRepositoryImpl implements TaskRepository{

    private final JpaTaskRepository jpaTaskRepository;
    private final MapperService mapperService;

    @Override
    public Task save(Task task){
        var entity  = mapperService.taskToEntity(task);
        var savedEntity = jpaTaskRepository.save(entity);
        return mapperService.taskToDomain(savedEntity);
    }

    @Override
    public Optional<Task> findById(Long id) {
        return jpaTaskRepository.findById(id)
            .map(mapperService::taskToDomain);
    }
    
}
