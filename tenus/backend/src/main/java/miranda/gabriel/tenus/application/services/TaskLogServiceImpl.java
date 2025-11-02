package miranda.gabriel.tenus.application.services;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import miranda.gabriel.tenus.adapters.inbounds.dto.tasklog.TaskLogRequestDTO;
import miranda.gabriel.tenus.application.usecases.AuthUseCases;
import miranda.gabriel.tenus.application.usecases.ImageUsecases;
import miranda.gabriel.tenus.application.usecases.TaskLogUsecases;
import miranda.gabriel.tenus.core.enums.ImageEntityType;
import miranda.gabriel.tenus.core.model.image.Image;
import miranda.gabriel.tenus.core.model.task.TaskRepository;
import miranda.gabriel.tenus.core.model.task_log.TaskLog;
import miranda.gabriel.tenus.core.model.task_log.TaskLogRepository;
import miranda.gabriel.tenus.infrastructure.exception.TenusExceptions;

@Service
@RequiredArgsConstructor
public class TaskLogServiceImpl implements TaskLogUsecases{

    private final TaskLogRepository taskLogRepository;
    private final AuthUseCases authService;
    private final TaskRepository taskRepository;
    private final ImageUsecases imageService;
    private final LocationValidationService locationValidationService;

    @Transactional
    public void createTaskLog(TaskLogRequestDTO dto, String userId) {
        var user = authService.validateUserId(userId);

        var task = taskRepository.findById(dto.getTaskId())
            .orElseThrow(() -> new TenusExceptions.TaskNotFoundException(dto.getTaskId()));

        if (!user.getId().equals(task.getBoard().getOwner().getId())) {
            throw new TenusExceptions.UnauthorizedOperationException("User does not have permission to register a log on this task");
        }

        if(!LocalDateTime.now().getDayOfWeek().equals(task.getDate().getDayOfWeek())) {
            throw new TenusExceptions.BusinessRuleViolationException("Task log can only be registered on the same day as the task date");
        }

        if(LocalTime.now().isBefore(task.getStartTime()) || LocalTime.now().isAfter(task.getEndTime())) {
            throw new TenusExceptions.BusinessRuleViolationException("Task log can only be registered within the task time frame");
        }

        var locationAccuracy = locationValidationService.validateProximity(
            dto.getLatitude(), 
            dto.getLongitude(), 
            task.getAddress()
        );

        Image image = null;
        if (dto.getImage() != null && !dto.getImage().isEmpty()) {  
            image = imageService.uploadImage(dto.getImage(), userId, ImageEntityType.TASK_LOG);
        }



        var tasklog = new TaskLog();
        tasklog.setTask(task);
        tasklog.setDescription(dto.getDescription());
        tasklog.setImage(image);
        tasklog.setUserLatitude(dto.getLatitude());
        tasklog.setUserLongitude(dto.getLongitude());
        tasklog.setLocationAccuracy(locationAccuracy);
        tasklog.setLogTime(LocalDateTime.now());
        tasklog.setPoints(3); // Pontos fixos por enquanto

        taskLogRepository.save(tasklog);

    }
}
