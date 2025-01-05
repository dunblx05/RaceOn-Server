package com.parting.dippin.api.member.service;

import com.parting.dippin.api.member.dto.GetProfileResDto;
import com.parting.dippin.domain.member.service.MemberReader;
import com.parting.dippin.entity.member.MemberEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final MemberReader memberReader;

    public GetProfileResDto getProfile(int memberId) {
        MemberEntity memberEntity = memberReader.getMemberById(memberId);

        return GetProfileResDto.from(memberEntity);
    }
}
