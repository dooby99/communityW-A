package com.example.cumstproj.service;

import com.example.cumstproj.domain.Board;
import com.example.cumstproj.domain.File;
import com.example.cumstproj.domain.dto.FileDto;
import com.example.cumstproj.repository.FileRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;

    @Transactional
    public Long save(FileDto fileDto) throws Exception{

        return fileRepository.save(fileDto.toEntity()).getId();
    }

    public List<File> find(Board board){
        return fileRepository.findByBoard(board);
    }


}
