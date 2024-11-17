package com.parting.dippin.domain.member.dao;

import com.parting.dippin.domain.member.dto.MemberDto;
import java.util.List;

public interface IMemberDAO {

    public List<MemberDto> getList();
}
