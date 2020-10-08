package com.api_board.service.board;

import com.api_board.domain.entity.Board;
import com.api_board.domain.entity.Comment;
import com.api_board.domain.entity.Image;
import com.api_board.domain.entity.User;
import com.api_board.domain.payload.request.BoardRequest;
import com.api_board.domain.payload.response.BoardListResponse;
import com.api_board.domain.payload.response.BoardResponse;
import com.api_board.domain.payload.response.CommentResponse;
import com.api_board.domain.repository.BoardRepository;
import com.api_board.domain.repository.CommentRepository;
import com.api_board.domain.repository.ImageRepository;
import com.api_board.domain.repository.UserRepository;
import com.api_board.exception.BoardNotFoundException;
import com.api_board.exception.UserNotFoundException;
import com.api_board.exception.UserNotSameException;
import com.api_board.security.AuthenticationFacade;
import com.api_board.service.comment.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final ImageRepository imageRepository;
    private final CommentRepository commentRepository;

    private final CommentService commentService;

    private final AuthenticationFacade authenticationFacade;

    @Value("${image.upload.dir}")
    private String imageDirPath;

    @SneakyThrows
    @Override
    public void write(BoardRequest boardRequest) {
        User user = userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        Board board = boardRepository.save(
                Board.builder()
                        .title(boardRequest.getTitle())
                        .content(boardRequest.getContent())
                        .author(user.getId())
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        for (MultipartFile image : boardRequest.getImages()) {
            String fileName = UUID.randomUUID().toString();

            imageRepository.save(
                    Image.builder()
                            .boardId(board.getId())
                            .imageName(fileName)
                            .build()
            );
            image.transferTo(new File(imageDirPath, fileName));
        }
    }

    @Override
    public List<BoardListResponse> boardList() {
        List<BoardListResponse> list = new ArrayList<>();

        for (Board board : boardRepository.findAllByOrderByCreatedAtAsc()) {
            User user = userRepository.findById(board.getAuthor())
                    .orElseThrow(UserNotFoundException::new);

            list.add(
                    BoardListResponse.builder()
                            .id(board.getId())
                            .title(board.getTitle())
                            .author(user.getName())
                            .createdAt(board.getCreatedAt())
                            .build()
            );
        }
        return list;
    }

    @Override
    public BoardResponse getBoard(Integer boardId) {
        User user = userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        Board board = boardRepository.findById(boardId)
                .orElseThrow(BoardNotFoundException::new);

        List<String> imageNames = new ArrayList<>();
        imageRepository.findByBoardId(boardId).forEach(image -> imageNames.add(image.getImageName()));

        List<CommentResponse> commentResponses = new ArrayList<>();

        for (Comment comment : commentRepository.findAllByBoardIdOrderByBoardIdAsc(boardId)) {
            User writer = userRepository.findById(comment.getUserId())
                    .orElseThrow(UserNotFoundException::new);

            List<Integer> subComment = comment.getChildComment().stream()
                    .map(Comment::getCommentId)
                    .collect(Collectors.toList());

            commentResponses.add(
                    CommentResponse.builder()
                            .boardId(boardId)
                            .content(comment.getContent())
                            .writer(writer.getName())
                            .createdAt(comment.getCreatedAt())
                            .child_comments(subComment)
                            .inMine(user.equals(writer))
                            .build()
            );
        }

        User author = userRepository.findById(board.getAuthor())
                .orElseThrow(UserNotFoundException::new);

        return BoardResponse.builder()
                .title(board.getTitle())
                .content(board.getContent())
                .author(author.getName())
                .images(imageNames)
                .comments(commentResponses)
                .createdAt(board.getCreatedAt())
                .build();
    }

    @SneakyThrows
    @Override
    public void modifyBoard(BoardRequest boardRequest, Integer boardId) {
        User user = userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        Board board = boardRepository.findById(boardId)
                .orElseThrow(BoardNotFoundException::new);

        if(!user.getId().equals(board.getAuthor()))
            throw new UserNotSameException();

        board.setter(boardRequest.getTitle(), boardRequest.getContent());

        boardRepository.save(board);

        List<Image> images = imageRepository.findByBoardId(boardId);

        for (Image image : images) {
            new File(imageDirPath, image.getImageName()).deleteOnExit();
        }

        imageRepository.deleteByBoardId(boardId);

        for (MultipartFile file : boardRequest.getImages()) {
            String fileName = UUID.randomUUID().toString();
            imageRepository.save(
                    Image.builder()
                            .boardId(boardId)
                            .imageName(fileName)
                            .build()
            );

            file.transferTo(new File(imageDirPath, fileName));
        }

    }

    @Override
    public void deleteBoard(Integer boardId) {
        User user = userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        Board board = boardRepository.findById(boardId)
                .orElseThrow(BoardNotFoundException::new);

        if(!user.getId().equals(board.getAuthor()))
            throw new UserNotSameException();

        for (Comment comment : commentRepository.findAllByBoardId(boardId)) {
            commentService.deleteComment(comment.getCommentId());
        }

        boardRepository.deleteById(boardId);
    }
}