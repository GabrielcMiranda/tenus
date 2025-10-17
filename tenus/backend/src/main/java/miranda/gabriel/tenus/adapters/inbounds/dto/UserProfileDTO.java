package miranda.gabriel.tenus.adapters.inbounds.dto;

import java.time.LocalDateTime;
import java.time.LocalTime;

public record UserProfileDTO(                 
    String username,          
    String email,               
    String phone,              
    LocalDateTime createdAt,    
    LocalTime messageTime,      
    Integer score              
) {}
