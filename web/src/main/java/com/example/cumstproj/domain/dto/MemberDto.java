package com.example.cumstproj.domain.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class MemberDto {

    private Long id;

    @NotEmpty(message = "필수 입력 입니다.")
    private String name;

    private String pw;
    private String pw2;
    private LocalDateTime localDateTime;
}
