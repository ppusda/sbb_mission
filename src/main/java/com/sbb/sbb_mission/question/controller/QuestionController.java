package com.sbb.sbb_mission.question.controller;

import ch.qos.logback.core.model.Model;
import com.sbb.sbb_mission.question.entity.Question;
import com.sbb.sbb_mission.question.service.QuestionService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/list")
    public List<Question> questionList() {
        return questionService.getQuestionList();
    }

    @GetMapping(value = "/detail/{id}")
    public Question questionDetail(@PathVariable("id") Long qid) {
        return questionService.getQuestion(qid);
    }

}
