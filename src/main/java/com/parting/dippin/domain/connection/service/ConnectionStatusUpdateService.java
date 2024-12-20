package com.parting.dippin.domain.connection.service;

import com.parting.dippin.domain.connection.ConnectionStatus;
import com.parting.dippin.entity.connection.ConnectionStatusEntity;
import com.parting.dippin.entity.connection.repository.ConnectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConnectionStatusUpdateService {

    private final ConnectionRepository connectionRepository;

    public void update(ConnectionStatus connectionStatus) {
        ConnectionStatusEntity connectionStatusEntity = ConnectionStatusEntity.from(connectionStatus);

        connectionRepository.save(connectionStatusEntity);
    }
}
