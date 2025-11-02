package miranda.gabriel.tenus.adapters.inbounds.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import miranda.gabriel.tenus.adapters.inbounds.dto.board.BoardDetailDTO;
import miranda.gabriel.tenus.adapters.inbounds.dto.board.BoardRequestDTO;
import miranda.gabriel.tenus.adapters.inbounds.dto.board.BoardResponseDTO;
import miranda.gabriel.tenus.adapters.inbounds.dto.task.TaskRequestDTO;
import miranda.gabriel.tenus.adapters.inbounds.dto.task.TaskSummaryDTO;
import miranda.gabriel.tenus.application.usecases.ActivityBoardUseCases;
import miranda.gabriel.tenus.application.usecases.TaskUsecases;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Slf4j
@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {

    private final ActivityBoardUseCases boardService;

    private final TaskUsecases taskService;

    @PostMapping("/create")
    public ResponseEntity<String> createBoard(@ModelAttribute BoardRequestDTO dto, @AuthenticationPrincipal Jwt jwt) {
        
        boardService.createBoard(dto, jwt.getSubject());
        
        return ResponseEntity.status(201).body("Activity board created successfully");
    }

    @GetMapping
    public ResponseEntity<List<BoardResponseDTO>> listUserBoards(@AuthenticationPrincipal Jwt jwt) {
        var boards = boardService.listUserBoards(jwt.getSubject());
        return ResponseEntity.ok(boards);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardDetailDTO> getBoard(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {
        var board = boardService.getBoard(id, jwt.getSubject());
        return ResponseEntity.ok(board);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {
        boardService.deleteBoard(id, jwt.getSubject());
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBoard(@PathVariable Long id, @ModelAttribute BoardRequestDTO dto, @AuthenticationPrincipal Jwt jwt) {
        boardService.updateBoard(id, dto, jwt.getSubject());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{boardId}/tasks")
    public ResponseEntity<String> createTask(@ModelAttribute TaskRequestDTO dto, @PathVariable Long boardId, @AuthenticationPrincipal Jwt jwt) {

        taskService.createTask(dto, boardId, jwt.getSubject());

        return ResponseEntity.status(201).body("Task created successfully");
    }

     @PutMapping("/{boardId}/tasks/{taskId}")
    public ResponseEntity<Void> updateTask(@ModelAttribute TaskRequestDTO dto, @PathVariable Long boardId, @PathVariable Long taskId, @AuthenticationPrincipal Jwt jwt) {

        taskService.updateTaskDTO(boardId, taskId, dto, jwt.getSubject());

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/tasks")
    public ResponseEntity<List<TaskSummaryDTO>> listTasks(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt, @RequestParam(required = false) String dayOfWeek) {
        var tasks = taskService.listTasks(id, jwt.getSubject(), dayOfWeek);
        return ResponseEntity.ok(tasks);
    }
    
    
    
    
}
