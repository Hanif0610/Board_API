package com.api_board.service.board;

import com.api_board.domain.entity.Board;
import com.api_board.domain.entity.File;
import com.api_board.domain.entity.User;
import com.api_board.domain.payload.request.BoardRequest;
import com.api_board.domain.payload.response.BoardListResponse;
import com.api_board.domain.payload.response.BoardResponse;
import com.api_board.domain.repository.BoardRepository;
import com.api_board.domain.repository.FileRepository;
import com.api_board.domain.repository.UserRepository;
import com.api_board.exception.BoardNotFoundException;
import com.api_board.exception.FileUploadException;
import com.api_board.exception.UserNotFoundException;
import com.api_board.exception.UserNotSameException;
import com.api_board.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.LocalDateTime.now;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    private final UserRepository userRepository;

    private final FileRepository fileRepository;

    @Value("${file.upload-dir}")
    private String fileLocation;

    @Override
    public void write(String token, BoardRequest boardRequest) {
        User user = userRepository.findById(JwtTokenUtil.parseAccessToken(token))
                .orElseThrow(UserNotFoundException::new);

        if(boardRequest.getFiles() != null) {
            List<File> files = Arrays.stream(boardRequest.getFiles())
                    .filter(file -> !file.isEmpty())
                    .map(this::storeFile)
                    .map(fileName -> new File(null, fileName, now()))
                    .map(fileRepository::save)
                    .collect(Collectors.toList());

            boardRepository.save(
                    Board.builder()
                            .title(boardRequest.getTitle())
                            .content(boardRequest.getContent())
                            .author(user.getName())
                            .userId(user.getId())
                            .createDate(LocalDate.now())
                            .files(files)
                            .build()
            );
        } else {
            boardRepository.save(
                    Board.builder()
                            .title(boardRequest.getTitle())
                            .content(boardRequest.getContent())
                            .author(user.getName())
                            .userId(user.getId())
                            .createDate(LocalDate.now())
                            .build()
            );
        }
    }

    @Override
    public List<BoardListResponse> boardList() {
        List<BoardListResponse> listBoard = new LinkedList<>();

        for (Board board : boardRepository.findAllByOrderByCreateDateAsc()) {
            listBoard.add(
                    BoardListResponse.builder()
                            .uuid(board.getId())
                            .title(board.getTitle())
                            .author(board.getAuthor())
                            .createdDate(board.getCreateDate())
                            .build()
            );
        }
        return listBoard;
    }

    @Override
    public BoardResponse getBoard(Integer id) {
        Board board = boardRepository.findById(id).orElseThrow(BoardNotFoundException::new);
        List<String> files = new LinkedList<>();
        for (File file : board.getFiles()) {
            files.add("http://localhost:8080/uploads/" + id + "/" + file.getUri());
        }

        return BoardResponse.builder()
                .title(board.getTitle())
                .content(board.getContent())
                .author(board.getAuthor())
                .uri(files)
                .createdDate(String.valueOf(board.getCreateDate()))
                .build();
    }

    @Override
    public void modifyBoard(String token, BoardRequest boardRequest, Integer id) {
        User user = userRepository.findById(JwtTokenUtil.parseAccessToken(token))
                .orElseThrow(UserNotFoundException::new);
        Board author = boardRepository.findById(JwtTokenUtil.parseAccessToken(token))
                .orElseThrow(BoardNotFoundException::new);

        if(!user.getId().equals(author.getUserId())) throw new UserNotSameException();

        Board board = boardRepository.findById(id).orElseThrow(BoardNotFoundException::new);

        board.setTitle(boardRequest.getTitle());
        board.setContent(boardRequest.getContent());
        board.setCreateDate(LocalDate.now());

        boardRepository.save(board);
    }

    @Override
    public void deleteBoard(String token, Integer id) {
        User user = userRepository.findById(JwtTokenUtil.parseAccessToken(token))
                .orElseThrow(UserNotFoundException::new);
        Board author = boardRepository.findById(JwtTokenUtil.parseAccessToken(token))
                .orElseThrow(BoardNotFoundException::new);
        if (user.getId().equals(author.getUserId())) throw new UserNotSameException();

        boardRepository.deleteAllById(id);
    }

    public String storeFile(MultipartFile file) {
        String fileName = file.getOriginalFilename();

        try {
            if (fileName.contains("..") || fileName.contains(" "))
                throw new FileUploadException("파일명에 부적합 문자가 포함되어 있습니다. " + fileName);

            file.transferTo(new java.io.File(fileLocation.concat(fileName)));
            return fileName;

        } catch (Exception e) {
            throw new FileUploadException("[" + fileName + "] 파일 업로드에 실패하였습니다. 다시 시도하십시오.", e);
        }
    }


}