package com.parting.dippin.domain.member.service;

import com.parting.dippin.core.common.CurrentTimeProvider;
import com.parting.dippin.entity.member.MemberEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberWithdrawService {

    private final MemberReader memberReader;
    private final CurrentTimeProvider currentTimeProvider;

    @Transactional
    public void withdraw(int memberId) {
        MemberEntity member = memberReader.getMemberById(memberId);

        member.withdraw(currentTimeProvider);
    }
}
