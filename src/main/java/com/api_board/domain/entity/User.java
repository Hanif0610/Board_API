package com.api_board.domain.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 30)
    private String email;

    @Column
    private String password;

    @Column
    private String name;
}
