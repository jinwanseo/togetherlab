package com.studytogether.account;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class AccountController {
 
    @GetMapping("/sign-up")
    public String signUpForm(Model model) {
        model.addAttribute("signUpForm", new SignUpForm());

        return "account/sign-up";
    }

    @PostMapping("/sign-up")
    public String signUpSubmit(@ModelAttribute SignUpForm signUpForm) {
        log.info("signUpForm = {}", signUpForm);
        // 회원가입 로직
        return "redirect:/";
    }
}
