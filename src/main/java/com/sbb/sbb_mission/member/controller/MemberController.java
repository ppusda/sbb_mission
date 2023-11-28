package com.sbb.sbb_mission.member.controller;

import com.sbb.sbb_mission.global.provider.JwtTokenProvider;
import com.sbb.sbb_mission.global.util.ValidateUtil;
import com.sbb.sbb_mission.member.request.MemberLoginRequest;
import com.sbb.sbb_mission.member.request.MemberRegisterRequest;
import com.sbb.sbb_mission.member.response.MemberCheckResponse;
import com.sbb.sbb_mission.member.response.MemberResponse;
import com.sbb.sbb_mission.member.service.MemberService;
import com.sbb.sbb_mission.member.validator.LoginValidator;
import com.sbb.sbb_mission.member.validator.RegisterValidator;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final ValidateUtil validateUtil;
    private final RegisterValidator registerValidator;
    private final LoginValidator loginValidator;
    private final JwtTokenProvider jwtTokenProvider;

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

    @GetMapping("/check")
    public MemberCheckResponse memberLoginCheck(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return MemberCheckResponse.builder()
                    .result(false)
                    .build();
        }

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                return MemberCheckResponse.builder()
                        .username(jwtTokenProvider.getClaims(cookie.getValue()).getSubject())
                        .result(jwtTokenProvider.validateToken(cookie.getValue()))
                        .build();
            }
        }

        return MemberCheckResponse.builder()
                .result(false)
                .build();
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok().build();
    }
}
