package com.sbb.sbb_mission.member.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record MemberRegisterRequest(
        @Size(min = 3, max = 25, message = "아이디는 3자 이상 25자 이하로 입력 해야합니다.")
        @NotEmpty(message = "ID는 필수항목입니다.")
        String username,
        @NotEmpty(message = "비밀번호는 필수항목입니다.")
        String password,
        @NotEmpty(message = "비밀번호 확인은 필수항목입니다.")
        String passwordCheck,
        @Email(message = "올바른 이메일 형식을 입력해 주시길 바랍니다.")
        @NotEmpty(message = "이메일은 필수항목입니다.")
        String email
) {

}
