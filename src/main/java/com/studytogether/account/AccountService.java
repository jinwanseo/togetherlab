package com.studytogether.account;

import com.studytogether.domain.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final JavaMailSender javaMailSender;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void processNewAccount(SignUpForm signUpForm) {
        Account newAccount = this.saveNewAccount(signUpForm);
        newAccount.generateEmailCheckToken();
        this.sendSignUpConfirmEmail(newAccount);
    }

    private Account saveNewAccount(SignUpForm signUpForm) {
        Account account = Account.builder().email(signUpForm.getEmail())
            .nickname(signUpForm.getNickname())
            .password(this.passwordEncoder.encode(signUpForm.getPassword())) // password encoding
            .studyCreatedByWeb(true)
            .studyEnrollmentResultByWeb(true)
            .studyUpdatedByWeb(true)
            .build();
        return this.accountRepository.save(account);
    }

    private void sendSignUpConfirmEmail(Account newAccount) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(newAccount.getEmail());
        mailMessage.setSubject("스터디투게더, 회원 가입 인증 메일");
        mailMessage.setText(
            "/check-email-token?token=" + newAccount.getEmailCheckToken() + "&email="
                + newAccount.getEmail()
        );
        this.javaMailSender.send(mailMessage);
    }

}
