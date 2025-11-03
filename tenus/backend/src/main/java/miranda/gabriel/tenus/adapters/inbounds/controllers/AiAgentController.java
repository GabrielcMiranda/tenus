package miranda.gabriel.tenus.adapters.inbounds.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import miranda.gabriel.tenus.adapters.inbounds.dto.ai.AiTaskScheduleRequestDTO;
import miranda.gabriel.tenus.adapters.inbounds.dto.ai.AiTaskScheduleResponseDTO;
import miranda.gabriel.tenus.application.usecases.AiAgentUseCases;

@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class AiAgentController {

    private final AiAgentUseCases aiAgentService;

    @PostMapping("/schedule-task")
    public ResponseEntity<AiTaskScheduleResponseDTO> scheduleTask(@RequestBody AiTaskScheduleRequestDTO dto, @AuthenticationPrincipal Jwt jwt) {
        
        AiTaskScheduleResponseDTO response = aiAgentService.scheduleTaskWithAi(dto, jwt.getSubject());
        
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }
}
