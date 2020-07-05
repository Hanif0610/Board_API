package com.api_board.service.board;

import com.api_board.domain.payload.request.BoardRequest;

public interface BoardService {

    void write(String token, BoardRequest boardRequest);
}
