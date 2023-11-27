package com.sbb.sbb_mission.member.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record MemberLoginRequest(

        @NotEmpty(message = "아이디를 입력해주세요.")
        String username,
        @NotEmpty(message = "비밀번호를 입력해주세요.")
        String password,
        String exception
) {

}
