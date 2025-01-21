package com.hello.ticketlink.user.controller;

import com.hello.ticketlink.dto.UserCreateRequestDto;
import com.hello.ticketlink.user.domain.User;
import com.hello.ticketlink.user.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;

import java.security.Principal;

@Slf4j
@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public String login() {
        return "user_login";
    }

    @GetMapping("/signup")
    public String signup(UserCreateRequestDto userCreateRequestDto) {
        return "user_signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute @Valid UserCreateRequestDto userCreateRequestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user_signup";
        }

        if (!userCreateRequestDto.getPassword1().equals(userCreateRequestDto.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect", "2개의 패스워드가 일치하지 않습니다.");
            return "user_signup";
        }

        userService.create(userCreateRequestDto.getUsername(),
                userCreateRequestDto.getEmail(), userCreateRequestDto.getPassword1());

        return "redirect:/login";
    }

    @GetMapping("/user")
    public String userInfo(Model model, Principal principal) {
        User user = userService.getUser(principal.getName());
        model.addAttribute("user", user.userInfoResponse());

        return "user_detail";
    }
}