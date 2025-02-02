package com.parting.dippin.api.game.service;

import com.parting.dippin.domain.game.AllGameTerminator;
import com.parting.dippin.domain.game.service.AllGameTerminatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Profile({"dev", "test"})
@Service
@RequiredArgsConstructor
public class GameAllTerminateService {

    private final AllGameTerminatorService allGameTerminatorService;

    @Transactional
    public void terminateAll() {
        AllGameTerminator allGameTerminator = new AllGameTerminator();

        allGameTerminator.terminateAll(allGameTerminatorService);
    }
}
