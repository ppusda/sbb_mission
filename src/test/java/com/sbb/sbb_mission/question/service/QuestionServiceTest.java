package com.sbb.sbb_mission.question.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sbb.sbb_mission.question.entity.Question;
import com.sbb.sbb_mission.question.repository.QuestionRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

        List<Question> questionList = questionService.getQuestionList();
        assertEquals(questionList.get(0).getId(), questionRepository.findAll().get(0).getId());
    }

    @Test
    @DisplayName("Question을 하나를 반환한다.")
    void getQuestion()  {
        questionService.addQuestion();

        Question question = questionService.getQuestion(1L);
        assertEquals(question.getId(), questionRepository.findById(1L).get().getId());
    }

}