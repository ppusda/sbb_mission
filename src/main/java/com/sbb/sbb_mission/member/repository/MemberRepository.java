package com.sbb.sbb_mission.member.repository;

import com.sbb.sbb_mission.member.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUsername(String username);
    Optional<Member> findByEmail(String email);
}
