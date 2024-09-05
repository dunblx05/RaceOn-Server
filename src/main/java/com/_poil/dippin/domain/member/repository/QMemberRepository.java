package com._poil.dippin.domain.member.repository;

import com._poil.dippin.domain.member.dto.MemberDto;
import java.util.List;

public interface QMemberRepository {

    public List<MemberDto> findMemberList();
}
