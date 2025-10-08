package miranda.gabriel.tenus.adapters.inbounds;

import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import miranda.gabriel.tenus.application.jwt.TokenResponseDTO;
import miranda.gabriel.tenus.application.usecases.AuthUseCases;
import miranda.gabriel.tenus.core.model.user.SignUpRequestDTO;
import miranda.gabriel.tenus.core.model.user.UserRequestDTO;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthUseCases authService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody SignUpRequestDTO dto) {
        
        authService.register(dto);
        
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login (@RequestBody UserRequestDTO dto) {
        var tokens = authService.login(dto);
        return ResponseEntity.ok(tokens);
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponseDTO> refresh(@RequestBody String refreshToken) {
        var tokens = authService.refresh(refreshToken);
        return ResponseEntity.ok(tokens);
    }
    
    

}
