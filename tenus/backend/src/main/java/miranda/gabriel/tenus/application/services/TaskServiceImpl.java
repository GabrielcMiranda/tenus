package miranda.gabriel.tenus.application.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import miranda.gabriel.tenus.adapters.inbounds.dto.address.AddressRequestDTO;
import miranda.gabriel.tenus.adapters.inbounds.dto.task.TaskRequestDTO;
import miranda.gabriel.tenus.adapters.inbounds.dto.task.TaskResponseDTO;
import miranda.gabriel.tenus.adapters.inbounds.dto.task.TaskSummaryDTO;
import miranda.gabriel.tenus.application.usecases.AuthUseCases;
import miranda.gabriel.tenus.application.usecases.ImageUsecases;
import miranda.gabriel.tenus.application.usecases.TaskUsecases;
import miranda.gabriel.tenus.core.enums.ImageEntityType;
import miranda.gabriel.tenus.core.enums.TaskStatus;
import miranda.gabriel.tenus.core.model.image.Image;
import miranda.gabriel.tenus.core.model.task.Task;
import miranda.gabriel.tenus.core.model.task.TaskRepository;
import miranda.gabriel.tenus.infrastructure.exception.TenusExceptions;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskUsecases{

    private final TaskRepository taskRepository;
    private final AuthUseCases authService;
    private final ImageUsecases imageService;

    @Transactional
    public void createTask(TaskRequestDTO dto, Long boardId, String userId) {
        var user = authService.validateUserId(userId);

        var board = user.getBoards().stream()
            .filter(b -> b.getId().equals(boardId))
            .findFirst()
            .orElseThrow(() -> new TenusExceptions.BoardNotFoundException(boardId));
        Image image = null;
        if (dto.getImage() != null && !dto.getImage().isEmpty()) {  
            image = imageService.uploadImage(dto.getImage(), userId, ImageEntityType.TASK);
        }

        if(dto.getStartTime().isBefore(user.getMessageTime()) || dto.getEndTime().isBefore(user.getMessageTime())) {
            throw new TenusExceptions.BusinessRuleViolationException("Task time cannot be before user's message time");
        }

        if(dto.getEndTime().isBefore(dto.getStartTime())) {
            throw new TenusExceptions.BusinessRuleViolationException("Task end time cannot be before start time");
        }

        if(dto.getDate().isBefore(LocalDate.now())) {
            throw new TenusExceptions.BusinessRuleViolationException("Task date cannot be in the past");
        }

        var task = new Task();
        task.setName(dto.getName());
        task.setBoard(board);
        task.setImage(image);
        task.setDescription(dto.getDescription());
        task.setDate(dto.getDate());
        task.setStartTime(dto.getStartTime());
        task.setEndTime(dto.getEndTime());
        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());
        task.setStatus(TaskStatus.PENDING);
        task.setCompleted(false);
        
        taskRepository.save(task);
    }

   @Transactional(readOnly = true)
    public List<TaskSummaryDTO> listTasks(Long boardId, String userId, String dayOfWeek) {
        var user = authService.validateUserId(userId);

        var board = user.getBoards().stream()
            .filter(b -> b.getId().equals(boardId))
            .findFirst()
            .orElseThrow(() -> new TenusExceptions.BoardNotFoundException(boardId));

        if(!board.getOwner().getId().toString().equals(userId)) {
            throw new TenusExceptions.UnauthorizedOperationException("User does not have access to this board");
        }

        if(dayOfWeek != null && !Arrays.asList("MONDAY","TUESDAY","WEDNESDAY","THURSDAY","FRIDAY","SATURDAY","SUNDAY").contains(dayOfWeek)) {
            throw new TenusExceptions.BusinessRuleViolationException("Invalid day of the week: " + dayOfWeek);
        }
        

        var tasks = board.getTasks().stream();
        
        if(dayOfWeek != null) {
            tasks = tasks.filter(task -> task.getDate().getDayOfWeek().name().equalsIgnoreCase(dayOfWeek));
        }

        var response = tasks.map(task -> new TaskSummaryDTO(
                task.getName(),
                task.getStartTime(),
                task.getEndTime()
            )).toList();

        return response;
    }

    public TaskResponseDTO getTask(Long taskId, String userId) {
        var user = authService.validateUserId(userId);

        var task = taskRepository.findById(taskId)
            .orElseThrow(() -> new TenusExceptions.TaskNotFoundException(taskId));
        
        if(!task.getBoard().getOwner().getId().equals(user.getId())) {
            throw new TenusExceptions.UnauthorizedOperationException("User does not have access to this task");
        }

        var response = new TaskResponseDTO(
            task.getId(),
            task.getName(),
            task.getImage() != null ? task.getImage().getImageUri() : null,
            task.getAddress() != null ? task.getAddress().fullAddress() : null,
            task.getDescription(),
            task.getDate().toString(),
            task.getStartTime().toString(),
            task.getEndTime().toString(),
            task.getStatus().name(),
            task.getCompleted()
        );

        return response;
    }

    @Transactional(readOnly = true)
    public AddressRequestDTO getTaskAddress(Long taskId, String userId) {
        var user = authService.validateUserId(userId);

        var task = taskRepository.findById(taskId)
            .orElseThrow(() -> new TenusExceptions.TaskNotFoundException(taskId));

        if(!task.getBoard().getOwner().getId().equals(user.getId())) {
            throw new TenusExceptions.UnauthorizedOperationException("User does not have access to this task");
        }

        if(task.getAddress() == null) {
            throw new TenusExceptions.BusinessRuleViolationException("Task does not have an address");
        }

        return new AddressRequestDTO(
            task.getAddress().getStreet(),
            task.getAddress().getNumber(),
            task.getAddress().getNeighbourhood(),
            task.getAddress().getCity(),
            task.getAddress().getState(),
            task.getAddress().getZipCode(),
            task.getAddress().getComplement()

        );
    }

}

