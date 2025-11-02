package miranda.gabriel.tenus.adapters.outbounds.repositories.implementations;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import miranda.gabriel.tenus.adapters.mappers.MapperService;
import miranda.gabriel.tenus.adapters.outbounds.entities.JpaAddressEntity;
import miranda.gabriel.tenus.adapters.outbounds.repositories.JpaTaskRepository;
import miranda.gabriel.tenus.core.model.task.Task;
import miranda.gabriel.tenus.core.model.task.TaskRepository;

@Repository
@RequiredArgsConstructor
public class TaskRepositoryImpl implements TaskRepository{

    private final JpaTaskRepository jpaTaskRepository;
    private final MapperService mapperService;
    private final EntityManager entityManager;

    @Override
    public Task save(Task task){
        var entity  = mapperService.taskToEntity(task);
        
        if (entity.getImage() != null && entity.getImage().getId() != null) {
            entity.setImage(entityManager.merge(entity.getImage()));
        }
        
        if (entity.getAddress() != null && entity.getAddress().getId() != null) {
            var managedAddress = entityManager.find(JpaAddressEntity.class, entity.getAddress().getId());
            if (managedAddress != null) {
                entity.setAddress(managedAddress);
            }
        }
        
        var savedEntity = jpaTaskRepository.save(entity);
        return mapperService.taskToDomain(savedEntity);
    }

    @Override
    public Optional<Task> findById(Long id) {
        return jpaTaskRepository.findById(id)
            .map(mapperService::taskToDomain);
    }
    
    @Override
    public void updateAddress(Long taskId, miranda.gabriel.tenus.core.model.address.Address address) {
        var taskEntity = jpaTaskRepository.findById(taskId)
            .orElseThrow(() -> new RuntimeException("Task not found"));
        
        if (address != null && address.getId() != null) {
            var addressEntity = entityManager.find(JpaAddressEntity.class, address.getId());
            taskEntity.setAddress(addressEntity);
        } else {
            taskEntity.setAddress(null);
        }
        
        entityManager.flush();
        entityManager.clear();
    }
    
}
