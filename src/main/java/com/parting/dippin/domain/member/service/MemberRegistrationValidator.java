package com.parting.dippin.domain.member.service;

import static com.parting.dippin.domain.member.exception.MemberCodeAndMessage.EXIST_MEMBER;

import com.parting.dippin.domain.member.exception.MemberTypeException;
import com.parting.dippin.entity.member.MemberEntity;
import com.parting.dippin.entity.member.enums.SocialProvider;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberRegistrationValidator {

    private final MemberReader memberReader;

    public void validateMemberDoesNotExist(String socialId, SocialProvider socialProvider) {
        Optional<MemberEntity> member = memberReader.getMemberByOauthId(socialId, socialProvider);

        if (member.isPresent()) {
            throw MemberTypeException.from(EXIST_MEMBER);
        }
    }
}
