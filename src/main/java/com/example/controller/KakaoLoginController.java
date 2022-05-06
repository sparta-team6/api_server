package com.example.controller;

import com.example.service.KakaoUserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@CrossOrigin(origins = "*")
public class KakaoLoginController {

    private final KakaoUserService kakaoUserService;

    @Autowired
    public KakaoLoginController(KakaoUserService kakaoUserService) {
        this.kakaoUserService = kakaoUserService;
    }

    // 임시로 만든 uri
    @GetMapping("/api/login")
    public String loginPage() {
        return "login";
    }

    // 카카오 로그인
    @GetMapping("/user/kakao/callback")
    public String kakaoLogin(@RequestParam String code) throws JsonProcessingException { // RequestParam 생략 가능
        kakaoUserService.kakaoLogin(code);
        return "redirect:/";
    }
}