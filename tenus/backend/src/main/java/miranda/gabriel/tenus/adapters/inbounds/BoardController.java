package miranda.gabriel.tenus.adapters.inbounds;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import miranda.gabriel.tenus.adapters.inbounds.dto.board.BoardDetailDTO;
import miranda.gabriel.tenus.adapters.inbounds.dto.board.BoardRequestDTO;
import miranda.gabriel.tenus.adapters.inbounds.dto.board.BoardResponseDTO;
import miranda.gabriel.tenus.application.usecases.ActivityBoardUseCases;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;





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

    @GetMapping
    public ResponseEntity<List<BoardResponseDTO>> listUserBoards(@AuthenticationPrincipal Jwt jwt) {
        var boards = boardUseCases.listUserBoards(jwt.getSubject());
        return ResponseEntity.ok(boards);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardDetailDTO> getBoard(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {
        var board = boardUseCases.getBoard(id, jwt.getSubject());
        return ResponseEntity.ok(board);
    }
    
    
    
}
