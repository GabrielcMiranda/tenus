package miranda.gabriel.tenus.application.services;

import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import miranda.gabriel.tenus.adapters.inbounds.dto.ai.AiTaskScheduleRequestDTO;
import miranda.gabriel.tenus.adapters.inbounds.dto.ai.AiTaskScheduleResponseDTO;
import miranda.gabriel.tenus.adapters.inbounds.dto.task.TaskScheduleRequestDTO;
import miranda.gabriel.tenus.adapters.outbounds.langchain.LangChainServicePort;
import miranda.gabriel.tenus.adapters.outbounds.langchain.dto.TaskScheduleExtraction;
import miranda.gabriel.tenus.application.usecases.AiAgentUseCases;
import miranda.gabriel.tenus.application.usecases.TaskUsecases;
import miranda.gabriel.tenus.core.model.task.Task;
import miranda.gabriel.tenus.core.model.task.TaskRepository;
import miranda.gabriel.tenus.infrastructure.exception.TenusExceptions;

@Service
@RequiredArgsConstructor
@Slf4j
public class AiAgentServiceImpl implements AiAgentUseCases {

    private final LangChainServicePort langChainService;
    private final TaskUsecases taskService;
    private final TaskRepository taskRepository;

    @Override
    @Transactional
    public AiTaskScheduleResponseDTO scheduleTaskWithAi(AiTaskScheduleRequestDTO dto, String userId) {
        
        log.info("Processing AI scheduling request for task {} by user {}", dto.getTaskId(), userId);
        log.info("User prompt: {}", dto.getPrompt());
        
        try {
            Task task = taskRepository.findById(dto.getTaskId())
                .orElseThrow(() -> new TenusExceptions.TaskNotFoundException(dto.getTaskId()));
      
            String currentTaskInfo = buildCurrentTaskInfo(task);
            
            TaskScheduleExtraction extraction = langChainService.extractTaskSchedule(
                dto.getPrompt(), 
                currentTaskInfo
            );
            
            log.info("AI extraction result: {}", extraction);
            
            if (extraction.getDate() == null && 
                extraction.getStartTime() == null && 
                extraction.getEndTime() == null) {
                
                return new AiTaskScheduleResponseDTO(
                    false,
                    "Não consegui entender o comando. Por favor, especifique data ou horário.",
                    extraction.getExplanation(),
                    null, null, null
                );
            }
            
            TaskScheduleRequestDTO scheduleUpdate = new TaskScheduleRequestDTO(
                extraction.getDate() != null ? extraction.getDate() : task.getDate(),
                extraction.getStartTime() != null ? extraction.getStartTime() : task.getStartTime(),
                extraction.getEndTime() != null ? extraction.getEndTime() : task.getEndTime()
            );
            
            taskService.updateTaskSchedule(dto.getTaskId(), scheduleUpdate, userId);
            
            return new AiTaskScheduleResponseDTO(
                true,
                "Tarefa reagendada com sucesso!",
                extraction.getExplanation(),
                extraction.getDate(),
                extraction.getStartTime(),
                extraction.getEndTime()
            );
            
        } catch (TenusExceptions.TaskNotFoundException e) {
            log.error("Task not found: {}", dto.getTaskId());
            return new AiTaskScheduleResponseDTO(
                false,
                "Tarefa não encontrada: " + e.getMessage(),
                null, null, null, null
            );
            
        } catch (TenusExceptions.UnauthorizedOperationException e) {
            log.error("Unauthorized operation: {}", e.getMessage());
            return new AiTaskScheduleResponseDTO(
                false,
                "Você não tem permissão para modificar esta tarefa: " + e.getMessage(),
                null, null, null, null
            );
            
        } catch (Exception e) {
            log.error("Error processing AI scheduling request", e);
            return new AiTaskScheduleResponseDTO(
                false,
                "Erro ao processar comando: " + e.getMessage(),
                null, null, null, null
            );
        }
    }

    private String buildCurrentTaskInfo(Task task) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        
        return String.format(
            "Tarefa: %s\nDescrição: %s\nData atual: %s\nHorário atual: %s - %s",
            task.getName(),
            task.getDescription() != null ? task.getDescription() : "Sem descrição",
            task.getDate().format(dateFormatter),
            task.getStartTime().format(timeFormatter),
            task.getEndTime().format(timeFormatter)
        );
    }
}
