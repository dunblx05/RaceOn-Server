package com.parting.dippin.domain.game;

import com.parting.dippin.domain.game.service.AllGameTerminatorService;

public class AllGameTerminator {

    public void terminateAll(
        AllGameTerminatorService allGameTerminatorService
    ) {
        allGameTerminatorService.terminateAll();
    }
}
