package miranda.gabriel.tenus.adapters.inbounds.dto.tasklog;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class TaskLogRequestDTO {

    private MultipartFile image;

    private String description;

    private Double latitude;

    private Double longitude;   
}
