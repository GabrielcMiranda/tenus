package miranda.gabriel.tenus.adapters.inbounds.dto.ai;

import lombok.Data;

@Data
public class AiTaskScheduleRequestDTO {
  
    private Long taskId;

    private String prompt;
}
