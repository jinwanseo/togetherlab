package com.studytogether.account;

import com.studytogether.domain.Account;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AccountController {

    private final SignUpFormValidator signUpFormValidator;
    private final AccountRepository accountRepository;
    private final JavaMailSender javaMailSender;

    @InitBinder("signUpForm")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(signUpFormValidator);
    }

    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("/sign-up")
    public String signUpForm(Model model) {
        model.addAttribute("signUpForm", new SignUpForm());

        return "account/sign-up";
    }

    @PostMapping("/sign-up")
    public String signUpSubmit(
        @Valid @ModelAttribute SignUpForm signUpForm,
        BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            return "account/sign-up";
        }

        Account account = Account.builder().email(signUpForm.getEmail())
            .nickname(signUpForm.getNickname())
            .password(signUpForm.getPassword()) //TODO password encoding
            .studyCreatedByWeb(true)
            .studyEnrollmentResultByWeb(true)
            .studyUpdatedByWeb(true)
            .build();

        Account newAccount = this.accountRepository.save(account);

        newAccount.generateEmailCheckToken();
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(newAccount.getEmail());
        mailMessage.setSubject("스터디투게더, 회원 가입 인증 메일");
        mailMessage.setText(
            "/check-email-token?token=" + newAccount.getEmailCheckToken() + "&email="
                + newAccount.getEmail()
        );
        javaMailSender.send(mailMessage);
        return "redirect:/";
    }
}
