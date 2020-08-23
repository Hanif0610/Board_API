package com.api_board.service.reply;

import com.api_board.domain.payload.request.ReplyRequest;
import com.api_board.domain.payload.request.ReplyUpdateRequest;
import com.api_board.domain.payload.response.ReplyResponse;

import java.util.List;

public interface ReplyService {

    Integer writeComments(String token, ReplyRequest replyRequest);
    List<ReplyResponse> getComments(Integer bno);
    void updateComments(String token, ReplyUpdateRequest replyUpdateRequest);
    void deleteComments(String token, Integer comment);
}
