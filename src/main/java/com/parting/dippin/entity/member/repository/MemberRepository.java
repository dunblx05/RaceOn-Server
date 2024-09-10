package com.parting.dippin.entity.member.repository;

import com.parting.dippin.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Integer>, QMemberRepository {

}
