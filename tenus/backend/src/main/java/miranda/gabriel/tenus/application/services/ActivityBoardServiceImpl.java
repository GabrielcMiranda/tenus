package miranda.gabriel.tenus.application.services;

import java.time.LocalDateTime;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import miranda.gabriel.tenus.adapters.inbounds.dto.board.BoardRequestDTO;
import miranda.gabriel.tenus.application.usecases.ActivityBoardUseCases;
import miranda.gabriel.tenus.application.usecases.AuthUseCases;
import miranda.gabriel.tenus.application.usecases.ImageUsecases;
import miranda.gabriel.tenus.core.model.activity_board.ActivityBoard;
import miranda.gabriel.tenus.core.model.activity_board.ActivityBoardRepository;
import miranda.gabriel.tenus.core.model.image.Image;
import miranda.gabriel.tenus.infrastructure.exception.TenusExceptions;

@Service
@RequiredArgsConstructor
public class ActivityBoardServiceImpl implements ActivityBoardUseCases{

    private final ActivityBoardRepository boardRepository;

    private final AuthUseCases authService;
    
    private final ImageUsecases imageService;

    public void createBoard(BoardRequestDTO dto, String userId){

        var user = authService.validateUserId(userId);

        if (dto.getName() == null || dto.getName().isEmpty()) {
            throw new TenusExceptions.BusinessRuleViolationException("Board name cannot be empty");
        }

        Image boardImage = null;
        if (dto.getImage() != null && !dto.getImage().isEmpty()) {
            boardImage = imageService.uploadImage(dto.getImage());
        }

        var board = new ActivityBoard();
        board.setName(dto.getName());
        board.setOwner(user);
        board.setImage(boardImage);
        board.setCreatedAt(LocalDateTime.now());
        board.setUpdatedAt(LocalDateTime.now());

        boardRepository.save(board);
    }
}