package com.parting.dippin.domain.member.service;

import static com.parting.dippin.domain.member.exception.MemberCodeAndMessage.INVALID_MEMBER_CODE;

import com.parting.dippin.domain.member.exception.MemberTypeException;
import com.parting.dippin.entity.member.MemberEntity;
import com.parting.dippin.entity.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberCodeCheckerService {

    private final MemberRepository memberRepository;

    /**
     * 멤버코드 유효성 체크 후 멤버아이디 반환
     *
     * @param memberCode
     * @return memberId
     * @throws MemberTypeException
     */
    public int invoke(String memberCode) {
        validateMemberCode(memberCode);

        MemberEntity member = memberRepository.findMemberEntityByMemberCode(memberCode)
                .orElseThrow(() -> MemberTypeException.from(INVALID_MEMBER_CODE));

        return member.getMemberId();
    }

    private void validateMemberCode(String memberCode) {
        boolean isValid = memberCode.matches("^[A-Z0-9]{6}$");

        if (!isValid) {
            throw MemberTypeException.from(INVALID_MEMBER_CODE);
        }
    }
}
