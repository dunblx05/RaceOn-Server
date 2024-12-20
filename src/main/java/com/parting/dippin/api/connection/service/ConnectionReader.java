package com.parting.dippin.api.connection.service;

import com.parting.dippin.entity.connection.ConnectionStatusEntity;
import com.parting.dippin.entity.connection.repository.ConnectionRepository;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConnectionReader {

    private final ConnectionRepository connectionRepository;

    public Map<Integer, ConnectionStatusEntity> getConnectionMap(List<Integer> memberIds) {
        List<ConnectionStatusEntity> connections = connectionRepository.findAllById(memberIds);

        return connections.stream()
                .collect(Collectors.toMap(
                        ConnectionStatusEntity::getMemberId,
                        Function.identity()
                ));
    }

}
