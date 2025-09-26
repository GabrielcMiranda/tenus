package miranda.gabriel.task_planner.adapters.inbounds;

import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import miranda.gabriel.task_planner.application.services.AuthServiceImpl;
import miranda.gabriel.task_planner.core.model.user.SignUpRequestDTO;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthServiceImpl authService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody SignUpRequestDTO dto) {
        
        authService.register(dto);
        
        return ResponseEntity.ok().build();
    }

}
