package miranda.gabriel.tenus.application.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import miranda.gabriel.tenus.adapters.inbounds.dto.address.AddressRequestDTO;
import miranda.gabriel.tenus.application.usecases.AddressUsecases;
import miranda.gabriel.tenus.application.usecases.AuthUseCases;
import miranda.gabriel.tenus.core.model.address.Address;
import miranda.gabriel.tenus.core.model.address.AddressRepository;
import miranda.gabriel.tenus.core.model.task.TaskRepository;
import miranda.gabriel.tenus.infrastructure.exception.TenusExceptions;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressUsecases{

    private final AddressRepository addressRepository;

    private final TaskRepository taskRepository;

    private final AuthUseCases authService;

    @Override
    @Transactional
    public void registerAddress(TaskType taskType, Long id, AddressRequestDTO dto, String userId) {
        var user = authService.validateUserId(userId);
        
        if (taskType == TaskType.TASK) {
            var task = taskRepository.findById(id)
                .orElseThrow(() -> new TenusExceptions.TaskNotFoundException(id));
            
            if (!task.getBoard().getOwner().getId().equals(user.getId())) {
                throw new TenusExceptions.UnauthorizedOperationException("User not authorized to add address to this task");
            }

            if(dto.zipCode() == null || dto.zipCode().length() < 8) {
                throw new TenusExceptions.BusinessRuleViolationException("invalid zip code");
            }

            var address = new Address();
            address.setStreet(dto.street());
            address.setCity(dto.city());
            address.setState(dto.state());
            address.setNeighbourhood(dto.neighbourhood());
            address.setNumber(dto.number());
            address.setZipCode(dto.zipCode());
            address.setComplement(dto.complement());

            var savedAddress = addressRepository.save(address);

            task.setAddress(savedAddress);
            
        } else if (taskType == TaskType.TASK_LOG) {
            // Lógica para registrar endereço de um log de tarefa
        }
    }
}

