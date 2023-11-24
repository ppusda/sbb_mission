package com.sbb.sbb_mission.answer.request;

import jakarta.validation.constraints.NotEmpty;

public record AnswerRequest(@NotEmpty(message = "내용은 필수 항목 입니다.") String content) {

}
