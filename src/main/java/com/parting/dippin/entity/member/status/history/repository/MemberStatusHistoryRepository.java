package com.parting.dippin.entity.member.status.history.repository;

import com.parting.dippin.entity.member.status.history.MemberStatusHistory;
import com.parting.dippin.entity.member.status.history.MemberStatusHistoryId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberStatusHistoryRepository extends JpaRepository<MemberStatusHistory, MemberStatusHistoryId>, QMemberStatusHistoryRepository {

}
