package com.api_board.controller;

import com.api_board.domain.payload.request.BoardRequest;
import com.api_board.domain.payload.request.ReplyRequest;
import com.api_board.domain.payload.response.BoardListResponse;
import com.api_board.domain.payload.response.BoardResponse;
import com.api_board.domain.payload.response.ReplyResponse;
import com.api_board.service.board.BoardService;
import com.api_board.service.reply.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    private final ReplyService replyService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/write", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void writeBoard(@RequestHeader("Authorization") @NotNull String token,
                           @ModelAttribute @Valid BoardRequest boardRequest) {
        boardService.write(token, boardRequest);
    }

    @GetMapping
    public List<BoardListResponse> boardList() {
        return boardService.boardList();
    }

    @GetMapping("/{id}")
    public BoardResponse getBoard(@PathVariable Integer id) {
        return boardService.getBoard(id);
    }

    @PutMapping("/{id}")
    public void modifyBoard(@RequestHeader("Authorization") @NotNull String token,
                            @RequestBody @Valid BoardRequest boardRequest, @PathVariable Integer id) {
        boardService.modifyBoard(token, boardRequest, id);
    }

    @DeleteMapping("/{id}")
    public void deleteBoard(@RequestHeader("Authorization") @NotNull String token,
                            @PathVariable Integer id) {
        boardService.deleteBoard(token, id);
    }
}
