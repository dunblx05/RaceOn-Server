package com._poil.dippin.domain.member.repository;

import com._poil.dippin.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Integer>, QMemberRepository {

}
