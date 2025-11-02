package miranda.gabriel.tenus.application.usecases;

import java.util.List;

import miranda.gabriel.tenus.adapters.inbounds.dto.address.AddressRequestDTO;
import miranda.gabriel.tenus.adapters.inbounds.dto.task.TaskRequestDTO;
import miranda.gabriel.tenus.adapters.inbounds.dto.task.TaskResponseDTO;
import miranda.gabriel.tenus.adapters.inbounds.dto.task.TaskSummaryDTO;

public interface TaskUsecases {

    public void createTask(TaskRequestDTO dto, Long boardId, String userId);

    public List<TaskSummaryDTO> listTasks(Long boardId, String userId, String dayOfWeek);

    public TaskResponseDTO getTask(Long taskId, String userId);

    public AddressRequestDTO getTaskAddress(Long taskId, String userId);

}
