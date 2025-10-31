package miranda.gabriel.tenus.core.model.task;

import java.util.Optional;

public interface TaskRepository {

    Task save(Task task);

    Optional<Task> findById(Long id);
}
