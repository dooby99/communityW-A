package com.example.cumstproj.controller;

import com.example.cumstproj.domain.Member;
import com.example.cumstproj.domain.dto.MemberDto;
import com.example.cumstproj.repository.MemberRepository;
import com.example.cumstproj.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;


    // 회원가입
    @GetMapping("/memberRegister")
    public String createForm(Model model, MemberDto memberDto) {
        model.addAttribute("memberForm", memberDto);
        return "member/register";
    }

    // sign-up ajax post
    @PostMapping("/memberRegister")
    public @ResponseBody String loginTest(@RequestBody @Validated MemberDto memberDto,
                                          HttpSession session) {
        try {
            Optional<Member> dupName = memberRepository.findByName(memberDto.getName());
            if (!memberDto.getPw().equals(memberDto.getPw2()) || memberDto.getPw().equals("") || memberDto.getPw2().equals("")) {
                log.info("[login fail!! not!! pw.equals(pw2)] >>>> id = {}, pw = {}, pw2 = {}", memberDto.getName(), memberDto.getPw(), memberDto.getPw());
                return "0";
            } else if (dupName.isPresent()) {
                if (memberDto.getName().equals(dupName.get().getName())) {
                    log.info("[login fail!! duplicated name] >>>> id = {}, pw = {}, pw2 = {}", memberDto.getName(), memberDto.getPw(), memberDto.getPw());
                    return "1";
                }
            } else {
                Member memberInfo = Member.builder()
                        .name(memberDto.getName())
                        .pw(memberDto.getPw())
                        .createAt(LocalDateTime.now())
                        .build();
                session.setAttribute("memberInfo", memberInfo);
                memberService.save(memberInfo);
                log.info("[login success!!!] >>>> name = {}", memberInfo.getName());
                return "9";
            }

        } catch (Exception e) {
            return e.getMessage();
        }
        return "redirect:/";
    }

    // login
    @GetMapping("/memberLogin")
    public String login() {
        return "member/login";
    }


    // Login post ajax
    @PostMapping("/memberLogin")
    public @ResponseBody String test(@RequestBody @Validated Member member,
                                     HttpServletResponse response,
                                     HttpSession session) throws Exception {

        try {
            Optional<Member> members = memberRepository.findByNameAndPw(member.getName(), member.getPw());
            if (members.isEmpty()) {
                log.info("[login fail!!] >>>>> id = {}, pw = {}", member.getName(), member.getPw());
                return "0";
            } else {
                members.ifPresent(e -> {
                    log.info("[Login Name] = {}", members.get().getName());
                    session.setAttribute("memberInfo", members.get());
                });
                return "login success";

            }
        } catch (Exception e) {
            return e.getMessage();
        }

    }

    // 로그아웃
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        log.info("logout");
        session.invalidate();
        return "redirect:/";
    }


}
