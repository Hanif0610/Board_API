package com.api_board.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Builder
@Table
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer uuid;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String author;

    private LocalDate createDate;
}
