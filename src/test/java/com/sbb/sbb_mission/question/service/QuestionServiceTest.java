package com.sbb.sbb_mission.question.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sbb.sbb_mission.question.entity.Question;
import com.sbb.sbb_mission.question.repository.QuestionRepository;
import com.sbb.sbb_mission.question.request.QuestionRequest;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

@SpringBootTest
class QuestionServiceTest {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    @DisplayName("Question List를 반환한다.")
    void getQuestionList()  {
        questionService.addQuestion();

        Page<Question> questionPage = questionService.getQuestionList(1);
        assertEquals(questionPage.toList().get(0).getId(), questionRepository.findAll().get(0).getId());
    }

    @Test
    @DisplayName("Question을 하나를 반환한다.")
    void getQuestion()  {
        questionService.addQuestion();

        Question question = questionService.getQuestion(1L);
        assertEquals(question.getId(), questionRepository.findById(1L).get().getId());
    }

    @Test
    @DisplayName("Question을 저장한다..")
    void saveQuestion()  {
        QuestionRequest questionRequest = QuestionRequest.builder()
                .subject("질문입니다.")
                .content("내용입니다.")
                .build();

        questionService.saveQuestion(questionRequest);

        Optional<Question> question = questionRepository.findById(1L);

        assertEquals(questionRequest.subject(), question.get().getSubject());
        assertEquals(questionRequest.content(), question.get().getContent());
    }

}