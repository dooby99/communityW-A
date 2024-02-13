package com.example.cumstproj.controller;

import com.example.cumstproj.domain.Board;
import com.example.cumstproj.domain.Comment;
import com.example.cumstproj.domain.Member;
import com.example.cumstproj.domain.dto.BoardDto;
import com.example.cumstproj.domain.dto.FileDto;
import com.example.cumstproj.repository.BoardRepository;
import com.example.cumstproj.repository.CommentRepository;
import com.example.cumstproj.repository.MemberRepository;
import com.example.cumstproj.service.BoardService;
import com.example.cumstproj.service.FileService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final FileService fileService;

    @Value("${custom.path}")
    private String uploadDir;

//    private String path = "/img/";


    // 글쓰기
    @GetMapping("/boardWrite")
    public String boardWrite(HttpServletResponse response,
                             HttpServletRequest request) {

        Member member = (Member) request.getSession().getAttribute("memberInfo");

        if (member == null) {
            log.info(">>> 로그인 후 이용해 주세요. <<<");
            try {
                response.setContentType("text/html; charset=utf-8");
                PrintWriter w = response.getWriter();
                w.write("<script>alert('" + "로그인 후 이용해 주세요." + "');history.go(-1);</script>");
                w.flush();
                w.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "member/login";
        }
        log.info(">>> boardWrite access <<<");
        return "board/boardWrite";
    }

    // 글쓰기 post
    @PostMapping("/boardWrite")
    public @ResponseBody String write(@RequestPart("json") @Validated BoardDto form,
                                      @RequestPart(value = "file", required = false) List<MultipartFile> uploadFile,
                                      HttpServletRequest request) throws Exception {
        try {
            Member member = (Member) request.getSession().getAttribute("memberInfo");
            Member member1 = memberRepository.findByName(member.getName()).orElseThrow();
            if (form.getTitle().equals("") || form.getContent().equals("")) {
                log.info("write fail!!! title or content isn't exist >>> title = {}, content = {}", form.getTitle(), form.getContent());
                return "0";

            } else {
                Board board = Board.builder()
                        .title(form.getTitle())
                        .content(form.getContent())
                        .createAt(LocalDateTime.now())
                        .subject(form.getSubject())
                        .member(member1)
                        .build();

                if (CollectionUtils.isEmpty(uploadFile)) {
                    log.info("업로드파일은 없는데 게시글 작성은완료후전~");
                    boardService.savePost(board);
                    log.info("업로드파일은 없는데 게시글 작성은완료후~");

                    return "9";
                }
                log.info("업로드 파일 있다 말했다.");
                boardService.savePost(board);

                //업로드 파일이 있으면 파일저장하고 끝
                for (MultipartFile multipartFile : uploadFile) {
                    String originalFileName = multipartFile.getOriginalFilename();
                    String saveFileName = createSaveFileName(multipartFile.getOriginalFilename());
                    // 서버에 파일 저장
                    multipartFile.transferTo(new File(getFullPath(saveFileName)));
                    log.info("file.getOriginalFilename = {}", multipartFile.getOriginalFilename());
                    log.info("fullPath = {}", saveFileName);

                    // DB에 정보 저장
                    String contentType = multipartFile.getContentType();

                    FileDto fileDto = FileDto.builder()
                            .originFileName(originalFileName)
                            .fullPath(uploadDir +saveFileName)
//                            .fullPath(path + saveFileName)
                            .saveFileName(saveFileName)
                            .contentType(contentType)
                            .boardId(board)
                            .createAt(LocalDateTime.now())
                            .build();

                    fileService.save(fileDto);
                }
                return "9";
            }


        } catch (Exception e) {
            return e.getMessage();
        }
    }

    // 게시글 보기
    @GetMapping("/board/{boardId}")
    public String viewBoard(Model model, @PathVariable("boardId") Long boardId) {
        Optional<Board> board = boardRepository.findById(boardId);
        var comment = commentRepository.findByBoard(board.get());

//        var files = fileRepository.findByFile(boardId);

        model.addAttribute("files", fileService.find(board.orElseThrow()));
        model.addAttribute("board", board);
        model.addAttribute("comment", comment);

        return "board/viewBoard";
    }

    // 게시글 삭제
    @GetMapping("/board/delete/{boardId}")
    public String deleteBoard(@PathVariable("boardId") Long boardId) {
        boardRepository.deleteById(boardId);
        log.info("delete board complete!");

        return "redirect:/";
    }

    // 게시글 수정
    @GetMapping("/board/edit/{boardId}")
    public String editBoard(@PathVariable("boardId") Long boardId,
                            Model model) {
        Optional<Board> board = boardRepository.findById(boardId);
        model.addAttribute("board", board);
        return "board/editBoard";
    }

    //게시글 수정 post
    @PostMapping("/board/edit/{boardId}")
    public String edit(@PathVariable("boardId") Long boardId,
                       Board board,
                       Model model) throws IOException {

        Board editBoard = boardService.findBoardOne(boardId);
        editBoard.setTitle(board.getTitle());
        editBoard.setContent(board.getContent());
        editBoard.setUpdateAt(LocalDateTime.now());
        editBoard.setSubject(board.getSubject());

        boardRepository.save(editBoard);
//        boardService.savePost(editBoard,board.getPhoto());

        return "redirect:/";
    }

    //댓글 post
    @GetMapping("/board/comment/{boardId}")
    public String writeComment(HttpServletRequest request, HttpServletResponse response,
                               Comment comment, Model model,
                               @PathVariable("boardId") Long boardId,
                               BindingResult result) {
//        if(result.hasErrors()){
//            log.info("error!");
//        }
        Member member = (Member) request.getSession().getAttribute("memberInfo");

        if (member == null) {
            try {
                response.setContentType("text/html; charset=utf-8");
                PrintWriter w = response.getWriter();
                w.write("<script>alert('" + "댓글 작성은 로그인 후 이용해 주세요." + "');history.go(-1);</script>");
                w.flush();
                w.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "redirect:/";
        }
        Board board = boardService.findBoardOne(boardId);

        if (comment.getContent().equals("")) {
            try {
                response.setContentType("text/html; charset=utf-8");
                PrintWriter w = response.getWriter();
                w.write("<script>alert('" + "댓글을 입력해 주세요" + "');history.go(-1);</script>");
                w.flush();
                w.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "redirect:/board/" + boardId;
        }

        Comment comment1 = Comment.builder()
                .content(comment.getContent())
                .createAt(LocalDateTime.now())
                .board(board)
                .member(member)
                .build();

        model.addAttribute("comment", comment1);
        commentRepository.save(comment1);


        return "redirect:/board/" + boardId;

    }

    @GetMapping("/testBoard")
    public List<Board> all(){
        boardRepository.findAll();

        return boardRepository.findAll();

    }

    // 파일 저장 이름 만들기
    // - 사용자들이 올리는 파일 이름이 같을 수 있으므로, 자체적으로 랜덤 이름을 만들어 사용한다
    private String createSaveFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    // 확장자명 구하기
    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

    // fullPath 만들기
    private String getFullPath(String filename) {
        return uploadDir + filename;
    }

}
