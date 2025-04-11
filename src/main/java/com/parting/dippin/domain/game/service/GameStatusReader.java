package com.parting.dippin.domain.game.service;

import static com.parting.dippin.core.exception.CommonCodeAndMessage.INVALID_USER_ID;

import com.parting.dippin.core.exception.CommonException;
import com.parting.dippin.entity.game.MemberGameStatusEntity;
import com.parting.dippin.entity.game.repository.MemberGameStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GameStatusReader {

    private final MemberGameStatusRepository memberGameStatusRepository;

    public MemberGameStatusEntity findByMemberId(int memberId) {
        return memberGameStatusRepository.findById(memberId)
                .orElseThrow(() -> CommonException.from(INVALID_USER_ID));
    }
}
