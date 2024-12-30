package com.parting.dippin.entity.member.repository;

import com.parting.dippin.entity.member.MemberEntity;
import com.parting.dippin.entity.member.enums.SocialProvider;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberEntity, Integer>, QMemberRepository {

    boolean existsByMemberCode(String memberCode);

    Optional<MemberEntity> findMemberEntityByMemberCode(String memberCode);

    Optional<MemberEntity> findMemberEntityByMemberId(Integer memberId);

    Optional<MemberEntity> findMemberEntityBySocialIdAndSocialProvider(String socialId, SocialProvider socialProvider);
}
