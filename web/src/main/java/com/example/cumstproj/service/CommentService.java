package com.example.cumstproj.service;


import com.example.cumstproj.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private CommentRepository commentRepository;


}
