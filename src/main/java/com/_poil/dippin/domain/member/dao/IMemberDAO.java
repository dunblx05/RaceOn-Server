package com._poil.dippin.domain.member.dao;

import com._poil.dippin.domain.member.dto.MemberDto;
import java.util.List;

public interface IMemberDAO {

    public List<MemberDto> find();
}
