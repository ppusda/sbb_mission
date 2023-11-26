package com.sbb.sbb_mission.member.service;

import com.sbb.sbb_mission.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("입력된 폼을 가지고 멤버를 저장할 수 있다.")
    void registerMember() {

    }


}