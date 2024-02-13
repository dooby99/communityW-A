package com.example.cumstproj.service;


import com.example.cumstproj.domain.Board;
import com.example.cumstproj.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public Long savePost(Board board) throws Exception {

//        log.info("file : {}", multipartFile);
//        if(multipartFile.isEmpty()){
//            log.info("empty file!!!!!!!!");
//        }
//            // 2. ������ ���� ���� & DB�� ���� ����(fileinfo) ����
//            // - ���� ���ϸ��� ���ϱ� ���� random�� ���
//            String originalFilename = multipartFile.getOriginalFilename();
//            String saveFileName = createSaveFileName(originalFilename);
//            // 2-1.������ ���� ����
//            multipartFile.transferTo(new File(getFullPath(saveFileName)));
//            // 2-2. DB�� ���� ����
//            String contentType = multipartFile.getContentType();
//            FileInfoRegister fileInfoRegister = FileInfoRegister.builder()
//                    .fileName(originalFilename)
//                    .saveFileName(saveFileName)
//                    .contentType(contentType)
//                    .build();
//            photoRepository.save(fileInfoRegister);

//        int fileInfoId = fileInfoDao.insert(fileInfoRegister);

        boardRepository.save(board);
        return board.getId();
    }

    public List<Board> boardList() {

        return boardRepository.findAll();
    }

    public List<Board> boardNoticeList() {
        return boardRepository.findNotice();
    }

    public List<Board> boardNormalList() {
        return boardRepository.findNormal();
    }

//    public void update(BoardDto boardDto){
//        Optional<Board> boards = boardRepository.findById(boardDto.getId());
//
//        if(boards.isPresent()){
//
//            boards.ifPresent(e->{
//                e.setTitle(boardDto.getTitle());
//                e.setComment(boardDto.getContent());
//                e.setLocalDateTime(boardDto.getLocalDateTime());
//                boardRepository.save(e);
//            });
//        }
//
//    }

    public Board findBoardOne(Long boardId) {
        return boardRepository.findById(boardId).get();

    }

    /*
     * save file on server & sve fileInfo on DB
     * */
///////////////////////////////////////////////////////////////////////////////

    // ���� ���� �̸� �����
// - ����ڵ��� �ø��� ���� �̸��� ���� �� �����Ƿ�, ��ü������ ���� �̸��� ����� ����Ѵ�
    private String createSaveFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    // Ȯ���ڸ� ���ϱ�
    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

    // fullPath �����
    private String getFullPath(String filename) {
        return filename;
//        return uploadPath + filename;
    }


}
