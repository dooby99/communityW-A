package com.example.cumstproj.repository;

import com.example.cumstproj.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board,Long> {

    // board subject is notice
//    @Query(value = "select * from Board where subject = 'NOTICE'", nativeQuery = true)
    @Query(value = "select b from Board b where b.subject = 'NOTICE'")
    List<Board> findNotice();

    // board subject is normal
//    @Query(value = "select * from Board where subject = 'NORMAL'", nativeQuery = true)
    @Query(value = "select b from Board b where b.subject = 'NORMAL'")
    List<Board> findNormal();

//    List<Board> findAllBoard();

//    List<Board> findById(Long id);

}
