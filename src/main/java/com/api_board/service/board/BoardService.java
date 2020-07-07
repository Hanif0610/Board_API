package com.api_board.service.board;

import com.api_board.domain.payload.request.BoardRequest;
import com.api_board.domain.payload.response.BoardListResponse;
import com.api_board.domain.payload.response.BoardResponse;

import java.util.List;

public interface BoardService {

    void write(String token, BoardRequest boardRequest);
    List<BoardListResponse> boardList();
    BoardResponse getBoard(String token, Integer uuid);
    void modifyBoard(String token, BoardRequest boardRequest, Integer uuid);
    void deleteBoard(String token, Integer uuid);
}
