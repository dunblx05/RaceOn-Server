package com.parting.dippin.entity.member.fcm.token.repository;

import com.parting.dippin.entity.member.fcm.token.MemberFcmToken;
import com.parting.dippin.entity.member.fcm.token.MemberFcmTokenId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberFcmTokenRepository extends JpaRepository<MemberFcmToken, MemberFcmTokenId>, QMemberFcmTokenRepository {

}
