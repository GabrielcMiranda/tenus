package miranda.gabriel.tenus.application.usecases;

import miranda.gabriel.tenus.adapters.inbounds.dto.tasklog.TaskLogRequestDTO;

public interface TaskLogUsecases {

    public void createTaskLog(Long taskId, TaskLogRequestDTO dto, String userId);
}
