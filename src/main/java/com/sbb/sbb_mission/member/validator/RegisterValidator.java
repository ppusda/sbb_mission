package com.sbb.sbb_mission.member.validator;

import com.sbb.sbb_mission.member.request.MemberRegisterRequest;
import com.sbb.sbb_mission.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class RegisterValidator implements Validator {

    private final static String MEMBER_ERROR_CODE = "memberServiceError";
    private final MemberService memberService;

    @Override
    public boolean supports(Class<?> clazz) {
        return MemberRegisterRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MemberRegisterRequest request = (MemberRegisterRequest) target;

        if (!request.password().equals(request.passwordCheck())) {
            errors.rejectValue("passwordCheck", MEMBER_ERROR_CODE, "비밀번호가 일치하지 않습니다.");
        }

        if (memberService.existsByUsername(request.username())) {
            errors.rejectValue("username", MEMBER_ERROR_CODE, "이미 존재하는 사용자 이름입니다.");
        }

        if (memberService.existsByEmail(request.email())) {
            errors.rejectValue("email", MEMBER_ERROR_CODE, "이미 존재하는 이메일 입니다.");
        }
    }
}
