package com.sbb.sbb_mission.question.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record QuestionRequest(
        @Size(max = 200)
        @NotEmpty(message = "제목은 필수 항목 입니다.")
        String subject,
        String content
){

}
