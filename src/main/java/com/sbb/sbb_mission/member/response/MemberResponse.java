package com.sbb.sbb_mission.member.response;

import com.sbb.sbb_mission.member.validator.MemberRole;
import lombok.Builder;

@Builder
public record MemberResponse (String username, MemberRole memberRole, String token){

}
