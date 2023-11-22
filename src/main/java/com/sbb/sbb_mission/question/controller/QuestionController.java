package com.sbb.sbb_mission.question.controller;

import com.sbb.sbb_mission.question.entity.Question;
import com.sbb.sbb_mission.question.service.QuestionService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/question/list")
    public List<Question> questionList() {
        return questionService.getQuestionList();
    }

}
