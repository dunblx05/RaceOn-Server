package com.parting.dippin.entity.friends.repository;

import com.parting.dippin.entity.member.MemberEntity;
import java.util.List;

public interface QFriendsRepository {

    List<MemberEntity> findByMemberId(int memberId);

    boolean existsFriendsByMyMemberIdAndMemberId(int myMemberId, int memberId);

    void deleteByMemberIdAndFriendId(int memberId, int friendId);
}
