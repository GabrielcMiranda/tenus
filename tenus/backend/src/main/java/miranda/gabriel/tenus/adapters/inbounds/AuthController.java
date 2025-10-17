package miranda.gabriel.tenus.adapters.inbounds;

import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import miranda.gabriel.tenus.adapters.inbounds.dto.LoginRequestDTO;
import miranda.gabriel.tenus.adapters.inbounds.dto.RefreshTokenRequestDTO;
import miranda.gabriel.tenus.adapters.inbounds.dto.SignUpRequestDTO;
import miranda.gabriel.tenus.adapters.inbounds.dto.TokenResponseDTO;
import miranda.gabriel.tenus.application.usecases.AuthUseCases;

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
    public ResponseEntity<String> register(@RequestBody SignUpRequestDTO dto) {
        
        authService.register(dto);

        return ResponseEntity.ok("User registered successfully.");
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login (@RequestBody LoginRequestDTO dto) {
        var tokens = authService.login(dto);
        return ResponseEntity.ok(tokens);
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponseDTO> refresh(@RequestBody RefreshTokenRequestDTO dto) {
        var tokens = authService.refresh(dto.refreshToken());
        return ResponseEntity.ok(tokens);
    }
    
    

}
