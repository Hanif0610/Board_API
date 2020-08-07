package com.api_board.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Builder
@Table
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Reply {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer rno;

    @Column(nullable = false)
    private Integer bno;

    @Column(nullable = false)
    private String writer;

    @Column(nullable = false)
    private String content;

    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reply> childComment;

    @ManyToOne
    @JoinColumn(name = "parent_comment")
    private Reply parentComment;
}
