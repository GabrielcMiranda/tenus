package miranda.gabriel.tenus.application.usecases;

import miranda.gabriel.tenus.adapters.inbounds.dto.ai.AiTaskScheduleRequestDTO;
import miranda.gabriel.tenus.adapters.inbounds.dto.ai.AiTaskScheduleResponseDTO;

public interface AiAgentUseCases {
    
    AiTaskScheduleResponseDTO scheduleTaskWithAi(AiTaskScheduleRequestDTO dto, String userId);
}
