package miranda.gabriel.tenus.application.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import miranda.gabriel.tenus.adapters.inbounds.dto.board.BoardDetailDTO;
import miranda.gabriel.tenus.adapters.inbounds.dto.board.BoardRequestDTO;
import miranda.gabriel.tenus.adapters.inbounds.dto.board.BoardResponseDTO;
import miranda.gabriel.tenus.adapters.inbounds.dto.task.TaskSummaryDTO;
import miranda.gabriel.tenus.application.usecases.ActivityBoardUseCases;
import miranda.gabriel.tenus.application.usecases.AuthUseCases;
import miranda.gabriel.tenus.application.usecases.ImageUsecases;
import miranda.gabriel.tenus.core.model.activity_board.ActivityBoard;
import miranda.gabriel.tenus.core.model.activity_board.ActivityBoardRepository;
import miranda.gabriel.tenus.core.model.image.Image;
import miranda.gabriel.tenus.infrastructure.exception.TenusExceptions;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActivityBoardServiceImpl implements ActivityBoardUseCases{

    private final ActivityBoardRepository boardRepository;

    private final AuthUseCases authService;
    
    private final ImageUsecases imageService;
    
    private final miranda.gabriel.tenus.core.model.user.UserRepository userRepository;

    public void createBoard(BoardRequestDTO dto, String userId){

        var user = authService.validateUserId(userId);

        var existingBoards = boardRepository.findAll().stream()
            .filter(board -> board.getOwner().getId().equals(user.getId()) && board.getName().equals(dto.getName()))
            .findAny();

        if(existingBoards.isPresent()) {
            throw new TenusExceptions.BusinessRuleViolationException("Board with the same name already exists");
        }

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

    @Transactional(readOnly = true)
    public List<BoardResponseDTO> listUserBoards(String userId){
        var user = authService.validateUserId(userId);

        var boards = boardRepository.findAll().stream()
            .filter(board -> board.getOwner().getId().equals(user.getId()))
            .map(board -> new BoardResponseDTO(
                board.getId(),
                board.getName(),
                board.getImage() != null ? board.getImage().getImageUri() : null,
                board.getTasks().size()
            )).toList();

        return boards;

    }

    @Transactional(readOnly = true)
    public BoardDetailDTO getBoard(Long boardId, String userId){
        var user = authService.validateUserId(userId);

        var board = boardRepository.findById(boardId)
            .orElseThrow(() -> new TenusExceptions.BoardNotFoundException(boardId));

        if (!board.getOwner().getId().equals(user.getId())) {
            throw new TenusExceptions.UnauthorizedOperationException("You do not have access to this board");
        }

        var tasksSummary = board.getTasks().stream()
            .map(task -> new TaskSummaryDTO(
                task.getName(),
                task.getStartTime(),
                task.getEndTime()
            )).toList();

        return new BoardDetailDTO(
            board.getName(),
            board.getImage() != null ? board.getImage().getImageUri() : null,
            tasksSummary
        );
    }

    @Transactional
    public void deleteBoard(Long boardId, String userId) {
        
        var user = authService.validateUserId(userId);

        var board = boardRepository.findById(boardId)
            .orElseThrow(() -> new TenusExceptions.BoardNotFoundException(boardId));

        if (!board.getOwner().getId().equals(user.getId())) {
            throw new TenusExceptions.UnauthorizedOperationException("You do not have access to this board");
        }

        var mutableBoards = new java.util.ArrayList<>(user.getBoards());
        mutableBoards.removeIf(b -> b.getId().equals(boardId));
        user.setBoards(mutableBoards);
        userRepository.save(user);

        if (board.getImage() != null) {
            imageService.deleteImage(board.getImage().getImageUri());
        }

        if (board.getTasks() != null && !board.getTasks().isEmpty()) {
            board.getTasks().stream()
                .filter(task -> task.getImage() != null)
                .forEach(task -> imageService.deleteImage(task.getImage().getImageUri()));
                }

        boardRepository.delete(board);
        
        }
    }