package miranda.gabriel.tenus.adapters.inbounds;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import miranda.gabriel.tenus.adapters.inbounds.dto.board.BoardRequestDTO;
import miranda.gabriel.tenus.application.usecases.ActivityBoardUseCases;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {

    private final ActivityBoardUseCases boardUseCases;

    @PostMapping("/create")
    public ResponseEntity<String> createBoard(@ModelAttribute BoardRequestDTO dto, @AuthenticationPrincipal Jwt jwt) {
        
        boardUseCases.createBoard(dto, jwt.getSubject());
        
        return ResponseEntity.status(201).body("Activity board created successfully");
    }
    
}
