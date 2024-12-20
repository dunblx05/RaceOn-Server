package com.parting.dippin.entity.friends.repository;

import com.parting.dippin.entity.member.MemberEntity;
import java.util.List;

public interface QFriendsRepository {

    public List<MemberEntity> findByMemberId(int memberId);

    public boolean existsFriendsByMyMemberIdAndMemberId(int myMemberId, int memberId);
}
