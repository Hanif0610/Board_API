package com.api_board.controller;

import com.api_board.domain.payload.request.BoardRequest;
import com.api_board.domain.payload.response.BoardListResponse;
import com.api_board.domain.payload.response.BoardResponse;
import com.api_board.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/write", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void writeBoard(@ModelAttribute @Valid BoardRequest boardRequest) {
        boardService.write(boardRequest);
    }

    @GetMapping
    public List<BoardListResponse> boardList() {
        return boardService.boardList();
    }

    @GetMapping("/{id}")
    public BoardResponse getBoard(@PathVariable Integer id) {
        return boardService.getBoard(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void modifyBoard(@ModelAttribute @Valid BoardRequest boardRequest, @PathVariable Integer id) {
        boardService.modifyBoard(boardRequest, id);
    }

    @DeleteMapping("/{id}")
    public void deleteBoard(@PathVariable Integer id) {
        boardService.deleteBoard(id);
    }
}
