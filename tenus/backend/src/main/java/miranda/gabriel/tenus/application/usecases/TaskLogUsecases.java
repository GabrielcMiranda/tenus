package miranda.gabriel.tenus.application.usecases;

import miranda.gabriel.tenus.adapters.inbounds.dto.tasklog.TaskLogRequestDTO;

public interface TaskLogUsecases {

    public void createTaskLog(TaskLogRequestDTO dto, String userId);
}
