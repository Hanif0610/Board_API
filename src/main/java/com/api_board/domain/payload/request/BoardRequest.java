package com.api_board.domain.payload.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class BoardRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @Nullable
    private MultipartFile[] files;

}
