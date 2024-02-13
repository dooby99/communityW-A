package com.example.cumstproj.domain.dto;



import com.example.cumstproj.domain.Board;
import com.example.cumstproj.domain.File;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class FileDto {
    private Long id;
    private String originFileName;
    private String fullPath;
    private String saveFileName;
    private String contentType;
    private Board boardId;
    private LocalDateTime createAt;

    public File toEntity() {
        return File.builder()
                .id(this.id)
                .originFileName(this.originFileName)
                .fullPath(this.fullPath)
                .saveFileName(this.saveFileName)
                .contentType(this.contentType)
                .createAt(this.createAt)
                .board(boardId)
                .build();
    }

}

