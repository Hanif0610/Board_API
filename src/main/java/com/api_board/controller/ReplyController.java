package com.api_board.controller;

import com.api_board.domain.payload.request.ReplyRequest;
import com.api_board.domain.payload.response.ReplyResponse;
import com.api_board.service.reply.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @PostMapping
    public Integer writeComment(@RequestHeader("Authorization") @NotNull String token,
                                @RequestBody @Valid ReplyRequest replyRequest) {
        return replyService.writeComments(token, replyRequest);
    }

    @GetMapping
    public List<ReplyResponse> getComment(@RequestParam Integer bno) {
        return replyService.getComments(bno);
    }
}