package com.api_board.service.reply;

import com.api_board.domain.payload.request.ReplyRequest;

public interface ReplyService {

    Integer writeComments(String token, Integer bno, ReplyRequest replyRequest);
}
