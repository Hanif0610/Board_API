package com.api_board.controller;

import com.api_board.domain.payload.request.BoardRequest;
import com.api_board.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/write")
    public void write(@RequestBody @Valid BoardRequest boardRequest,
                      @RequestHeader("Authorization") String refreshToken) {
        boardService.write(refreshToken, boardRequest);
    }

}
