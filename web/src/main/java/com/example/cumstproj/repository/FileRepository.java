package com.example.cumstproj.repository;

import com.example.cumstproj.domain.Board;
import com.example.cumstproj.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {


    List<File> findByBoard (Board board);
}
