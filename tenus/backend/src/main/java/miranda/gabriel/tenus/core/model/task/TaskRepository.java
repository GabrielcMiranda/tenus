package miranda.gabriel.tenus.core.model.task;

import java.util.Optional;

import miranda.gabriel.tenus.core.model.address.Address;

public interface TaskRepository {

    Task save(Task task);

    Optional<Task> findById(Long id);
    
    void updateAddress(Long taskId, Address address);
}
