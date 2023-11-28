package com.sbb.sbb_mission.member.service;

import com.sbb.sbb_mission.global.provider.JwtTokenProvider;
import com.sbb.sbb_mission.member.entity.Member;
import com.sbb.sbb_mission.member.repository.MemberRepository;
import com.sbb.sbb_mission.member.request.MemberLoginRequest;
import com.sbb.sbb_mission.member.request.MemberRegisterRequest;
import com.sbb.sbb_mission.member.response.MemberResponse;
import com.sbb.sbb_mission.member.validator.MemberRole;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void registerMember(MemberRegisterRequest memberRegisterRequest) {
        Member member = Member.builder()
                .username(memberRegisterRequest.username())
                .password(passwordEncoder.encode(memberRegisterRequest.password()))
                .email(memberRegisterRequest.email())
                .build();

        memberRepository.save(member);
    }

    public MemberResponse loginMember(MemberLoginRequest memberLoginRequest) {
        Member member = memberRepository.findByUsername(memberLoginRequest.username())
                .filter(value -> passwordEncoder.matches(memberLoginRequest.password(), value.getPassword()))
                .orElseThrow(IllegalArgumentException::new);

        MemberRole memberRole = MemberRole.USER;
        if (checkAdmin(member.getUsername())) {
            memberRole = MemberRole.ADMIN;
        }

        return MemberResponse.builder()
                .username(member.getUsername())
                .memberRole(memberRole)
                .token(jwtTokenProvider.createToken(member.getUsername()))
                .build();
    }

    public Member getMember(String username) {
        Optional<Member> member = this.memberRepository.findByUsername(username);
        if (member.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return member.get();
    }

    public boolean existsByUsername(String username) {
        Optional<Member> member = memberRepository.findByUsername(username);
        return member.isPresent();
    }

    public boolean existsByEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        return member.isPresent();
    }

    public boolean checkAdmin(String username) {
        return username.equals(MemberRole.ADMIN.getValue());
    }

    public boolean checkUser(String username, String password) {
        Optional<Member> member = memberRepository.findByUsername(username);

        return member.filter(value -> passwordEncoder.matches(password, value.getPassword()))
                .isPresent();
    }

}
