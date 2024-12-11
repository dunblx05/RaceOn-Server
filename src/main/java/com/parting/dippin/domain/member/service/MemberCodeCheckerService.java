package com.parting.dippin.domain.member.service;

import com.parting.dippin.domain.member.exception.InvalidMemberCodeException;
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
     * @throws InvalidMemberCodeException
     */
    public int invoke(String memberCode) {
        validateMemberCode(memberCode);

        MemberEntity member = memberRepository.findMemberEntityByMemberCode(memberCode).orElseThrow(
            InvalidMemberCodeException::new);

        return member.getMemberId();
    }

    private void validateMemberCode(String memberCode) {
        boolean isValid = memberCode.matches("^[A-Z0-9]{6}$");

        if (!isValid) {
            throw new InvalidMemberCodeException();
        }
    }
}
