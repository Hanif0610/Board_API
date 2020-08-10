package com.api_board.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
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

    @Column
    private Integer bno;

    @Column
    private String writer;

    @Column
    private String content;

    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL)
    private List<Reply> childComment;

    @ManyToOne
    @JoinColumn(name = "parent_comment")
    private Reply parentComment;

    private LocalDate createAt;
}
