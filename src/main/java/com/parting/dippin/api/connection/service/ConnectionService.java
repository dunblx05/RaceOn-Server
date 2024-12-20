package com.parting.dippin.api.connection.service;

import com.parting.dippin.core.common.CurrentTimeProvider;
import com.parting.dippin.domain.connection.ConnectionStatus;
import com.parting.dippin.domain.connection.service.ConnectionStatusUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConnectionService {

    private final ConnectionStatusUpdateService connectionStatusUpdateService;
    private final CurrentTimeProvider currentTimeProvider;

    public void updateConnectionStatus(int memberId) {
        ConnectionStatus connectionStatus = ConnectionStatus.builder()
                .memberId(memberId)
                .lastActiveAt(currentTimeProvider.now())
                .isPlaying(false)
                .build();

        connectionStatus.update(connectionStatusUpdateService);
    }
}
