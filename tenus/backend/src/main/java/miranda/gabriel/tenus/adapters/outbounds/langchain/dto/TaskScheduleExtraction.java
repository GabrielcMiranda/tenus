package miranda.gabriel.tenus.adapters.outbounds.langchain.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskScheduleExtraction {
    
    private LocalDate date;

    private LocalTime startTime;
    
    private LocalTime endTime;
    
    private String explanation;
}
