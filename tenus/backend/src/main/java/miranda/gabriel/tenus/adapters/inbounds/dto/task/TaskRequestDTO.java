package miranda.gabriel.tenus.adapters.inbounds.dto.task;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class TaskRequestDTO {

    private Long boardId;

    private String name;

    private MultipartFile image;

    private String description;

    private LocalDate date;

    private LocalTime startTime;    

    private LocalTime endTime; 
}
