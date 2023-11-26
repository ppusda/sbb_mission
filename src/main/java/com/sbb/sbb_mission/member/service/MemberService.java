package com.sbb.sbb_mission.member.service;

import com.sbb.sbb_mission.member.entity.Member;
import com.sbb.sbb_mission.member.repository.MemberRepository;
import com.sbb.sbb_mission.member.request.MemberRegisterRequest;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean existsByUsername(String username) {
        Optional<Member> member = memberRepository.findByUsername(username);
        return member.isPresent();
    }

    public boolean existsByEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        return member.isPresent();
    }

    public void registerMember(MemberRegisterRequest memberRegisterRequest) {
        Member member = Member.builder()
                .username(memberRegisterRequest.username())
                .password(passwordEncoder.encode(memberRegisterRequest.password()))
                .email(memberRegisterRequest.email())
                .build();

        memberRepository.save(member);
    }

}
