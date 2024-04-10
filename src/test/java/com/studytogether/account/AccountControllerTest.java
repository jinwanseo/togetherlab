package com.studytogether.account;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.studytogether.domain.Account;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @DisplayName("이메일 인증 테스트 - 입력값 오류")
    @Test
    void checkEmailToken_with_wrong_input() throws Exception {
        mockMvc.perform(get("/check-email-token")
                .param("token", "sdlkfjsdflk")
                .param("email", "jinwanseo@gmail.com")
            ).andDo(print())
            .andExpect(status().isOk())
            .andExpect(view().name("account/checked-email"))
            .andExpect(model().attributeExists("error"))
            .andExpect(model().attributeDoesNotExist("nickname"));
    }

    @DisplayName("이메일 인증 테스트 - 입력값 정상")
    @Test
    void checkEmailToken_with_correct_input() throws Exception {

        Account account = Account.builder().email("admin@gmail.com").password("!Q2w3e4r5t")
            .nickname("admin").build();
        account.generateEmailCheckToken();
        Account newAccount = this.accountRepository.save(account);

        mockMvc.perform(get("/check-email-token")
                .param("token", newAccount.getEmailCheckToken())
                .param("email", newAccount.getEmail())
            ).andDo(print())
            .andExpect(status().isOk())
            .andExpect(view().name("account/checked-email"))
            .andExpect(model().attributeDoesNotExist("error"))
            .andExpect(model().attributeExists("nickname"))
            .andExpect(model().attributeExists("numberOfUser"));

    }


    @DisplayName("회원 가입 화면 보이는지 테스트")
    @Test
    void signUpForm() throws Exception {
        mockMvc.perform(get("/sign-up"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(view().name("account/sign-up"))
            .andExpect(model().attributeExists("signUpForm"));
    }

    @DisplayName("회원 가입 처리 - 입력값 오류")
    @Test
    void signupSubmitWithWrongInput() throws Exception {

        mockMvc.perform(post("/sign-up")
                .param("nickname", "admin")
                .param("email", "jinwan")
                .param("password", "!Q2w3e4r5t")
                .with(csrf())
            )
            .andExpect(status().isOk())
            .andExpect(view().name("account/sign-up"));
    }

    @DisplayName("회원 가입 처리 - 입력값 정상")
    @Test
    void signupSubmitWithSuccessInput() throws Exception {

        mockMvc.perform(post("/sign-up")
                .param("nickname", "admin3")
                .param("email", "admin3@naver.com")
                .param("password", "!Q2w3e4r5t")
                .with(csrf())
            )
            .andExpect(view().name("redirect:/"));

        Account account = this.accountRepository.findByEmail("admin3@naver.com");
        Assertions.assertThat(account).isNotNull();
        boolean matches = this.passwordEncoder.matches("!Q2w3e4r5t", account.getPassword());
        Assertions.assertThat(matches).isTrue();
        Assertions.assertThat(account.getEmailCheckToken()).isNotNull();
    }

}