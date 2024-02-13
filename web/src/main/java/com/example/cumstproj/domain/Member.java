package com.example.cumstproj.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String name;
    private String pw;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    private List<Board> board = new ArrayList<>();


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    private List<Comment> comment = new ArrayList<>();


}
