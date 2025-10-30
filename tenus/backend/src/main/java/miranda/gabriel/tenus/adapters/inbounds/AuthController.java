package miranda.gabriel.tenus.adapters.inbounds;

import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import miranda.gabriel.tenus.adapters.inbounds.dto.auth.LoginRequestDTO;
import miranda.gabriel.tenus.adapters.inbounds.dto.auth.RefreshTokenRequestDTO;
import miranda.gabriel.tenus.adapters.inbounds.dto.auth.SignUpRequestDTO;
import miranda.gabriel.tenus.adapters.inbounds.dto.auth.TokenResponseDTO;
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

        return ResponseEntity.status(201).body("User registered successfully.");
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
