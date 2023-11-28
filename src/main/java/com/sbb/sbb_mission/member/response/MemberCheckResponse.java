package com.sbb.sbb_mission.member.response;

import lombok.Builder;

@Builder
public record MemberCheckResponse(String username, boolean result) {

}
