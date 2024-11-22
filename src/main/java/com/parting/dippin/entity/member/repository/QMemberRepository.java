package com.parting.dippin.entity.member.repository;

import com.parting.dippin.domain.member.dto.MemberDto;
import java.util.List;

public interface QMemberRepository {

    public List<MemberDto> findMemberAndIsFriendByNicknameAndMemberId(String nickname,
        int myMemberId);
}
