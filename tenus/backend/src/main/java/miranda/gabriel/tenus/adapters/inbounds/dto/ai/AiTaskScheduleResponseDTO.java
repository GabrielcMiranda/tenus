package miranda.gabriel.tenus.adapters.inbounds.dto.ai;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AiTaskScheduleResponseDTO {
    
    private boolean success;
    
    private String message;
    
    private String aiExplanation;
    
    private LocalDate newDate;
    
    private LocalTime newStartTime;
    
    private LocalTime newEndTime;
}
