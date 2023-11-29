package com.sbb.sbb_mission.answer.controller;

import com.sbb.sbb_mission.answer.entity.Answer;
import com.sbb.sbb_mission.answer.request.AnswerRequest;
import com.sbb.sbb_mission.answer.service.AnswerService;
import com.sbb.sbb_mission.global.util.ValidateUtil;
import com.sbb.sbb_mission.member.entity.Member;
import com.sbb.sbb_mission.member.service.MemberService;
import com.sbb.sbb_mission.question.entity.Question;
import com.sbb.sbb_mission.question.request.QuestionRequest;
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

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/modify/{id}")
    public void writeAnswer(@Valid AnswerRequest answerRequest, @PathVariable("id") Long id, Principal principal) {
        answerService.modifyAnswer(answerRequest, id, principal.getName());
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/remove/{id}")
    public void removeAnswer(@PathVariable("id") Long id, Principal principal) {
        answerService.removeAnswer(id, principal.getName());
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/vote/{id}")
    public void answerVote(Principal principal, @PathVariable("id") Long id) {
        Answer answer = answerService.getAnswer(id);
        Member siteUser = memberService.getMember(principal.getName());

        answerService.vote(answer, siteUser);
    }
}
