package miranda.gabriel.tenus.infrastructure.exception;

import java.time.LocalDateTime;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    
    private LocalDateTime timestamp;
    
    private int status;
  
    private String error;
  
    private String message;
  
    private String path;

    private Map<String, String> fieldErrors;
    
    private String errorCode;
}