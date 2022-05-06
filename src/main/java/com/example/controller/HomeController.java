package com.example.controller;

import com.example.login.SessionConst;
import com.example.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

//@Controller
@RestController
@RequiredArgsConstructor
public class HomeController {

//    @GetMapping("/")
//    public String home(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) User loginMember, Model model) {
//        // 세션에 회원 데이터가 없으면 home
//        if (loginMember == null) {
//            return "index";
//        }
//        model.addAttribute("user", loginMember);
//        return "index";
//    }

    @GetMapping("/")
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }
}
