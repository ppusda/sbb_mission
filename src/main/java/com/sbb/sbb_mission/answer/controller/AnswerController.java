package com.sbb.sbb_mission.answer.controller;

import com.sbb.sbb_mission.answer.request.AnswerRequest;
import com.sbb.sbb_mission.answer.service.AnswerService;
import com.sbb.sbb_mission.global.util.ValidateUtil;
import com.sbb.sbb_mission.question.entity.Question;
import com.sbb.sbb_mission.question.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
    private final ValidateUtil validateUtil;

    @PostMapping("/write/{id}")
    public ResponseEntity<?> createAnswer(@PathVariable("id") Long qid, @Valid AnswerRequest answerRequest, BindingResult bindingResult) {
        if (validateUtil.hasErrors(bindingResult)) {
            return validateUtil.getErrors(bindingResult);
        }

        Question question = questionService.getQuestion(qid);
        answerService.writeAnswer(question, answerRequest);
        return ResponseEntity.ok().build();
    }
}
