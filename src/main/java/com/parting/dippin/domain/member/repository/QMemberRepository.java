package com.parting.dippin.domain.member.repository;

import com.parting.dippin.domain.member.dto.MemberDto;
import java.util.List;

public interface QMemberRepository {

    public List<MemberDto> findMemberList();
}
