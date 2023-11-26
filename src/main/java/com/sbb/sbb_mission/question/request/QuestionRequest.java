package com.sbb.sbb_mission.question.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record QuestionRequest(@NotEmpty(message = "제목은 필수 항목 입니다.") @Size(max = 200) String subject, String content){
}
