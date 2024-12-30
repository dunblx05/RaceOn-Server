package com.parting.dippin.api.connection;

import com.parting.dippin.api.connection.service.ConnectionService;
import com.parting.dippin.core.base.BaseResponse;
import com.parting.dippin.core.common.annotation.LoggedInMemberId;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ConnectionController {

    private final ConnectionService connectionService;

    @PutMapping("/connection-status")
    public BaseResponse<Void> getMembers(@LoggedInMemberId Integer memberId) {
        connectionService.updateConnectionStatus(memberId);

        return BaseResponse.ok();
    }
}
