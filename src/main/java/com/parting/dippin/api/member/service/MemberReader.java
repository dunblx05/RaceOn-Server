package com.parting.dippin.api.member.service;

import com.parting.dippin.domain.member.dao.IMemberDAO;
import com.parting.dippin.domain.member.dto.MemberDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberReader {

    private final IMemberDAO memberDAO;

    public List<MemberDto> getMembers(String nickname, int myMemberId) {
        return this.memberDAO.getMembers(nickname, myMemberId);
    }
}
