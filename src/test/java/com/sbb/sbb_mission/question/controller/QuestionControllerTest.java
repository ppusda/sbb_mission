package com.sbb.sbb_mission.question.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sbb.sbb_mission.question.entity.Question;
import com.sbb.sbb_mission.question.request.QuestionRequest;
import com.sbb.sbb_mission.question.service.QuestionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class QuestionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private QuestionService questionService;

    @Test
    @DisplayName("Question List를 조회한다.")
    void getQuestionList() throws Exception {
        questionService.addQuestion();

        mockMvc.perform(get("/question/list")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("Question 하나를 조회한다.")
    void getQuestion() throws Exception {
        questionService.addQuestion();

        mockMvc.perform(get("/question/detail/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("Question을 등록한다.")
    void saveQuestion() throws Exception {
        mockMvc.perform(post("/question/write")
                        .param("subject", "질문 제목")
                        .param("content", "질문 내용"))
                .andExpect(status().isOk())
                .andDo(print());

        Question question = questionService.getQuestion(1L);

        assertEquals("질문 제목", question.getSubject());
        assertEquals("질문 내용", question.getContent());
    }

    @Test
    @DisplayName("Question 등록 시 제목을 입력하지 않으면 오류가 발생한다.")
    void saveQuestionNoSubject() throws Exception {
        mockMvc.perform(post("/question/write")
                        .param("subject", "")
                        .param("content", "질문 내용"))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }
}