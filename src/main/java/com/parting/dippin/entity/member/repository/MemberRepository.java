package com.parting.dippin.entity.member.repository;

import com.parting.dippin.entity.member.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberEntity, Integer>, QMemberRepository {

    boolean existsByMemberCode(String memberCode);
}
