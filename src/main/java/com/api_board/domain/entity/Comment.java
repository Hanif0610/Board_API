package com.api_board.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commentId;

    private Integer boardId;

    @Column
    private String content;

    @Column
    private Integer userId;

    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL)
    private List<Comment> childComment;

    @ManyToOne
    @JoinColumn(name = "parent_comment")
    private Comment parentComment;

    private LocalDateTime createdAt;
}
