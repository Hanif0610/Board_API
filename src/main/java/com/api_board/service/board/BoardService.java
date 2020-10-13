package com.api_board.service.board;

import com.api_board.domain.payload.request.BoardRequest;
import com.api_board.domain.payload.response.ApplicationListResponse;
import com.api_board.domain.payload.response.BoardResponse;
import org.springframework.data.domain.Pageable;

public interface BoardService {

    void write(BoardRequest boardRequest);
    ApplicationListResponse boardList(Pageable pageable);
    BoardResponse getBoard(Integer boardId);
    void modifyBoard(BoardRequest boardRequest, Integer boardId);
    void deleteBoard(Integer boardId);
}
