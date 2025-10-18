package miranda.gabriel.tenus.application.services;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import miranda.gabriel.tenus.adapters.inbounds.dto.board.BoardRequestDTO;
import miranda.gabriel.tenus.application.usecases.AuthUseCases;
import miranda.gabriel.tenus.core.model.activity_board.ActivityBoard;
import miranda.gabriel.tenus.core.model.activity_board.ActivityBoardRepository;

@Service
@RequiredArgsConstructor
public class ActivityBoardServiceImpl {

    private final ActivityBoardRepository boardRepository;

    private final AuthUseCases authService;

    public void createBoard(BoardRequestDTO dto, String userId){

        var user = authService.validateUserId(userId);

        if (dto.getName() == null || dto.getName().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Board name cannot be empty");
        }

        var board = new ActivityBoard();
        board.setName(dto.getName());
        board.setOwner(user);
        board.setCreatedAt(LocalDateTime.now());
        board.setUpdatedAt(LocalDateTime.now());

        boardRepository.save(board);
    }
}