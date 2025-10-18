package miranda.gabriel.tenus.application.usecases;

import miranda.gabriel.tenus.adapters.inbounds.dto.board.BoardRequestDTO;

public interface ActivityBoardUseCases {

    public void createBoard(BoardRequestDTO dto, String userId);
}
