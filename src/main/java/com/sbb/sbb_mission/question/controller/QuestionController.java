package com.sbb.sbb_mission.question.controller;

import ch.qos.logback.core.model.Model;
import com.sbb.sbb_mission.global.util.ValidateUtil;
import com.sbb.sbb_mission.question.entity.Question;
import com.sbb.sbb_mission.question.request.QuestionRequest;
import com.sbb.sbb_mission.question.service.QuestionService;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;
    private final ValidateUtil validateUtil;

    @GetMapping("/list")
    public List<Question> getQuestionList() {
        return questionService.getQuestionList();
    }

    @GetMapping(value = "/detail/{id}")
    public Question getQuestionDetail(@PathVariable("id") Long qid) {
        return questionService.getQuestion(qid);
    }

    @PostMapping(value = "/write")
    public ResponseEntity<?> writeQuestion(@Valid QuestionRequest questionRequest, BindingResult bindingResult) {
        if (validateUtil.hasErrors(bindingResult)) {
            return validateUtil.getErrors(bindingResult);
        }

        questionService.saveQuestion(questionRequest);
        return ResponseEntity.ok().build();
    }

}
