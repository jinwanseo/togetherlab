package com.studytogether.account;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountRepository accountRepository;


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
                .param("nickname", "jinwanseo")
                .param("email", "jinwan")
                .param("password", "Tjwjdgh3@@")
                .with(csrf())
            )
            .andExpect(status().isOk())
            .andExpect(view().name("account/sign-up"));
    }

    @DisplayName("회원 가입 처리 - 입력값 정상")
    @Test
    void signupSubmitWithSuccessInput() throws Exception {

        mockMvc.perform(post("/sign-up")
                .param("nickname", "jinwanseo")
                .param("email", "jinwanseo@naver.com")
                .param("password", "Tjwjdgh3@@")
                .with(csrf())
            )
            .andExpect(status().is(302))
            .andExpect(view().name("redirect:/"));

        assertTrue(accountRepository.existsByEmail("jinwanseo@naver.com"));
    }

}