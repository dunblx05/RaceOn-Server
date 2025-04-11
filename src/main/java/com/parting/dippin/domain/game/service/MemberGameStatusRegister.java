package com.parting.dippin.domain.game.service;

import com.parting.dippin.entity.game.MemberGameStatusEntity;
import com.parting.dippin.entity.game.repository.MemberGameStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberGameStatusRegister {

    private final MemberGameStatusRepository memberGameStatusRepository;

    public void save(int memberId) {
        MemberGameStatusEntity entity = new MemberGameStatusEntity(memberId);
        memberGameStatusRepository.save(entity);
    }
}

