package com.sbb.sbb_mission.member.controller;

import com.sbb.sbb_mission.global.util.ValidateUtil;
import com.sbb.sbb_mission.member.request.MemberRegisterRequest;
import com.sbb.sbb_mission.member.service.MemberService;
import com.sbb.sbb_mission.member.validator.MemberValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final MemberValidator memberValidator;
    private final ValidateUtil validateUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid MemberRegisterRequest memberRegisterRequest, BindingResult bindingResult) {
        memberValidator.validate(memberRegisterRequest, bindingResult);

        if (validateUtil.hasErrors(bindingResult)) {
            return validateUtil.getErrors(bindingResult);
        }

        memberService.registerMember(memberRegisterRequest);
        return ResponseEntity.ok().build();
    }
}
