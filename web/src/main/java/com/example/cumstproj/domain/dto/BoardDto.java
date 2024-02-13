package com.example.cumstproj.domain.dto;

import com.example.cumstproj.domain.BoardSubject;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDto {

    private Long id;

//    @NotEmpty(message = "제목입력은 필수 입니다.")
    private String title;

//    @NotEmpty(message = "내용입력은 필수 입니다.")
    private String content;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    private BoardSubject subject;
    private Long memberId;

    private MultipartFile file;

}
