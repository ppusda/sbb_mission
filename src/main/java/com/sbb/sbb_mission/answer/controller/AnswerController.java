package com.sbb.sbb_mission.answer.controller;

import com.sbb.sbb_mission.answer.request.AnswerRequest;
import com.sbb.sbb_mission.answer.service.AnswerService;
import com.sbb.sbb_mission.question.entity.Question;
import com.sbb.sbb_mission.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/answer")
public class AnswerController {

    private final QuestionService questionService;
    private final AnswerService answerService;

    @PostMapping("/write/{id}")
    public void createAnswer(@PathVariable("id") Long qid, AnswerRequest answerRequest) {
        Question question = questionService.getQuestion(qid);

        answerService.writeAnswer(question, answerRequest);
    }
}
