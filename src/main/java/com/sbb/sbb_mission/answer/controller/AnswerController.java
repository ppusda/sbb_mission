package com.sbb.sbb_mission.answer.controller;

import com.sbb.sbb_mission.answer.request.AnswerRequest;
import com.sbb.sbb_mission.answer.service.AnswerService;
import com.sbb.sbb_mission.global.util.ValidateUtil;
import com.sbb.sbb_mission.member.entity.Member;
import com.sbb.sbb_mission.member.service.MemberService;
import com.sbb.sbb_mission.question.entity.Question;
import com.sbb.sbb_mission.question.service.QuestionService;
import jakarta.validation.Valid;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final MemberService memberService;
    private final ValidateUtil validateUtil;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/write/{id}")
    public ResponseEntity<?> createAnswer(@PathVariable("id") Long qid, @Valid AnswerRequest answerRequest,
            BindingResult bindingResult, Principal principal) {
        if (validateUtil.hasErrors(bindingResult)) {
            return validateUtil.getErrors(bindingResult);
        }

        Question question = questionService.getQuestion(qid);
        Member member = memberService.getMember(principal.getName());
        answerService.writeAnswer(question, answerRequest, member);
        return ResponseEntity.ok().build();
    }
}
