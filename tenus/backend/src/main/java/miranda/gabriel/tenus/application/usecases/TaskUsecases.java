package miranda.gabriel.tenus.application.usecases;

import miranda.gabriel.tenus.adapters.inbounds.dto.task.TaskRequestDTO;

public interface TaskUsecases {

    public void createTask(TaskRequestDTO dto, Long boardId, String userId);
}
