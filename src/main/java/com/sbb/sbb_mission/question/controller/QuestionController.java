package com.sbb.sbb_mission.question.controller;

import com.sbb.sbb_mission.global.util.ValidateUtil;
import com.sbb.sbb_mission.member.entity.Member;
import com.sbb.sbb_mission.member.service.MemberService;
import com.sbb.sbb_mission.question.entity.Question;
import com.sbb.sbb_mission.question.request.QuestionRequest;
import com.sbb.sbb_mission.question.service.QuestionService;

import jakarta.validation.Valid;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;
    private final MemberService memberService;
    private final ValidateUtil validateUtil;

    @GetMapping("/list")
    public Page<Question> getQuestionList(@RequestParam(value="page", defaultValue="0") int page) {
        return questionService.getQuestionList(page);
    }

    @GetMapping(value = "/detail/{id}")
    public Question getQuestionDetail(@PathVariable("id") Long qid) {
        return questionService.getQuestion(qid);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/write")
    public ResponseEntity<?> writeQuestion(@Valid QuestionRequest questionRequest,
            BindingResult bindingResult, Principal principal) {
        if (validateUtil.hasErrors(bindingResult)) {
            return validateUtil.getErrors(bindingResult);
        }

        Member member = memberService.getMember(principal.getName());
        questionService.saveQuestion(questionRequest, member);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/modify/{id}")
    public void modifyQuestion(@Valid QuestionRequest questionRequest, @PathVariable("id") Long id,
            Principal principal) {

        questionService.modifyQuestion(questionRequest, id, principal.getName());
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/remove/{id}")
    public void removeQuestion(@PathVariable("id") Long id, Principal principal) {
        questionService.removeQuestion(id, principal.getName());
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/vote/{id}")
    public void questionVote(Principal principal, @PathVariable("id") Long id) {
        Question question = questionService.getQuestion(id);
        Member siteUser = memberService.getMember(principal.getName());

        questionService.vote(question, siteUser);
    }


    @PostMapping("/search")
    public Page<Question> list(@RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "keyword", defaultValue = "") String keyword) {
        return this.questionService.searchQuestionList(page, keyword);
    }

}
