package miranda.gabriel.tenus.application.services;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import miranda.gabriel.tenus.adapters.inbounds.dto.task.TaskRequestDTO;
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
    public void createTask(TaskRequestDTO dto, String userId) {
        var user = authService.validateUserId(userId);

        var board = user.getBoards().stream()
            .filter(b -> b.getId().equals(dto.getBoardId()))
            .findFirst()
            .orElseThrow(() -> new TenusExceptions.BoardNotFoundException(dto.getBoardId()));
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

}
