package com.parting.dippin.entity.member.term.repository;

import com.parting.dippin.entity.member.term.MemberTerm;
import com.parting.dippin.entity.member.term.MemberTermId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberTermRepository extends JpaRepository<MemberTerm, MemberTermId>, QMemberTermRepository {

}
