package com.api_board.controller;

import com.api_board.domain.payload.request.BoardRequest;
import com.api_board.domain.payload.response.BoardListResponse;
import com.api_board.domain.payload.response.BoardResponse;
import com.api_board.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/write")
    public void write(@RequestHeader("Authorization") @NotNull String token,
                      @RequestBody @Valid BoardRequest boardRequest) {
        boardService.write(token, boardRequest);
    }

    @GetMapping("/list")
    public List<BoardListResponse> boardList() {
        return boardService.boardList();
    }

    @GetMapping("/list/{uuid}")
    public BoardResponse getBoard(@RequestHeader("Authorization") @NotNull String token,
                                  @PathVariable Integer uuid) {
        return boardService.getBoard(token, uuid);
    }

    @PutMapping("/list/{uuid}")
    public void modify(@RequestHeader("Authorization") @NotNull String token,
                       @RequestBody @Valid BoardRequest boardRequest, @PathVariable Integer uuid) {
        boardService.modifyBoard(token, boardRequest, uuid);
    }

    @DeleteMapping("/list/{uuid}")
    public void deleteBoard(@RequestHeader("Authorization") @NotNull String token,
                            @PathVariable Integer uuid) {
        boardService.deleteBoard(token, uuid);
    }
}
