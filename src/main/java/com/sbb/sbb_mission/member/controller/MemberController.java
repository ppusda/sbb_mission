package com.sbb.sbb_mission.member.controller;

import com.sbb.sbb_mission.global.util.ValidateUtil;
import com.sbb.sbb_mission.member.request.MemberLoginRequest;
import com.sbb.sbb_mission.member.request.MemberRegisterRequest;
import com.sbb.sbb_mission.member.service.MemberService;
import com.sbb.sbb_mission.member.validator.LoginValidator;
import com.sbb.sbb_mission.member.validator.RegisterValidator;
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
    private final ValidateUtil validateUtil;
    private final RegisterValidator registerValidator;
    private final LoginValidator loginValidator;


    @PostMapping("/register")
    public ResponseEntity<?> memberRegister(@Valid MemberRegisterRequest memberRegisterRequest, BindingResult bindingResult) {
        registerValidator.validate(memberRegisterRequest, bindingResult);

        if (validateUtil.hasErrors(bindingResult)) {
            return validateUtil.getErrors(bindingResult);
        }

        memberService.registerMember(memberRegisterRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> memberLogin(@Valid MemberLoginRequest memberLoginRequest, BindingResult bindingResult) {
        loginValidator.validate(memberLoginRequest, bindingResult);

        if (validateUtil.hasErrors(bindingResult)) {
            return validateUtil.getErrors(bindingResult);
        }

        return ResponseEntity.ok(memberService.loginMember(memberLoginRequest));
    }
}
