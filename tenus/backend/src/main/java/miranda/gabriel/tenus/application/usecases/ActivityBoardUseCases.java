package miranda.gabriel.tenus.application.usecases;

import java.util.List;

import miranda.gabriel.tenus.adapters.inbounds.dto.board.BoardDetailDTO;
import miranda.gabriel.tenus.adapters.inbounds.dto.board.BoardRequestDTO;
import miranda.gabriel.tenus.adapters.inbounds.dto.board.BoardResponseDTO;

public interface ActivityBoardUseCases {

    public void createBoard(BoardRequestDTO dto, String userId);

    public List<BoardResponseDTO> listUserBoards(String userId);

    public BoardDetailDTO getBoard(Long boardId, String userId);

    public void deleteBoard(Long boardId, String userId);
}
