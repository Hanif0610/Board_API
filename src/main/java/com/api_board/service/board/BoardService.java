package com.api_board.service.board;

import com.api_board.domain.payload.request.BoardRequest;
import com.api_board.domain.payload.response.BoardListResponse;
import com.api_board.domain.payload.response.BoardResponse;

import java.util.List;

public interface BoardService {

    void write(BoardRequest boardRequest);
    List<BoardListResponse> boardList();
    BoardResponse getBoard(Integer id);
    void modifyBoard(BoardRequest boardRequest, Integer id);
    void deleteBoard(Integer id);
}
