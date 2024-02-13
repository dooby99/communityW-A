package com.example.cumstproj.controller;

import com.example.cumstproj.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final BoardService boardService;

    @RequestMapping("/")
    public String home(Model model) {
        // 게시글 띄우기
        model.addAttribute("list", boardService.boardList());

        // board (notice)
        model.addAttribute("noticeList",boardService.boardNoticeList());

        // board (normal)
        model.addAttribute("normalList",boardService.boardNormalList());

        return "home";
    }

}