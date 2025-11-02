package miranda.gabriel.tenus.application.usecases;

import miranda.gabriel.tenus.adapters.inbounds.dto.address.AddressRequestDTO;

public interface AddressUsecases {

    public void registerAddress(TaskType taskType, Long id, AddressRequestDTO dto, String userId);

    public void updateAddress(TaskType taskType, Long id, AddressRequestDTO dto, String userId);

    public enum TaskType {
        TASK,
        TASK_LOG
    }
}
