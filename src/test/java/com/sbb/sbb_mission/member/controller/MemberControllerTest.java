package com.sbb.sbb_mission.member.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sbb.sbb_mission.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("회원 등록을 할 수 있다.")
    void registerMember() throws Exception {
        mockMvc.perform(post("/member/register")
                        .param("username", "test")
                        .param("password", "1234")
                        .param("passwordCheck", "1234")
                        .param("email", "test@naver.com"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("회원 등록 시 아이디는 필수 항목이다.")
    void registerMemberWithoutUsername() throws Exception {
        mockMvc.perform(post("/member/register")
                        .param("username", "")
                        .param("password", "1234")
                        .param("passwordCheck", "1234")
                        .param("email", "test@naver.com"))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @Test
    @DisplayName("회원 등록 시 아이디는 3자 이상 25자 이하여야 한다.")
    void registerMemberUsernameSize() throws Exception {
        mockMvc.perform(post("/member/register")
                        .param("username", "te")
                        .param("password", "1234")
                        .param("passwordCheck", "1234")
                        .param("email", "test@naver.com"))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @Test
    @DisplayName("회원 등록 시 비밀번호는 필수 항목이다.")
    void registerMemberWithoutPassword() throws Exception {
        mockMvc.perform(post("/member/register")
                        .param("username", "test")
                        .param("password", "")
                        .param("passwordCheck", "1234")
                        .param("email", "test@naver.com"))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @Test
    @DisplayName("회원 등록 시 비밀번호 확인은 필수 항목이다.")
    void registerMemberWithoutPasswordCheck() throws Exception {
        mockMvc.perform(post("/member/register")
                        .param("username", "test")
                        .param("password", "1234")
                        .param("passwordCheck", "")
                        .param("email", "test@naver.com"))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @Test
    @DisplayName("회원 등록 시 이메일은 필수 항목이다.")
    void registerMemberWithoutEmail() throws Exception {
        mockMvc.perform(post("/member/register")
                        .param("username", "test")
                        .param("password", "1234")
                        .param("passwordCheck", "1234")
                        .param("email", ""))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @Test
    @DisplayName("회원 등록 시 이메일 형식에 맞지 않는 문자열은 입력될 수 없다.")
    void registerMemberWithoutEmailPattern() throws Exception {
        mockMvc.perform(post("/member/register")
                        .param("username", "test")
                        .param("password", "1234")
                        .param("passwordCheck", "1234")
                        .param("email", "test@"))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }

}