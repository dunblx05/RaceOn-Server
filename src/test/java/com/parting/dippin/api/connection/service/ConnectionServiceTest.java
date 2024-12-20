package com.parting.dippin.api.connection.service;

import static org.assertj.core.api.Assertions.*;

import com.parting.dippin.common.DatabaseTest;
import com.parting.dippin.common.TestCurrentTimeProvider;
import com.parting.dippin.domain.connection.service.ConnectionStatusUpdateService;
import com.parting.dippin.entity.connection.ConnectionStatusEntity;
import com.parting.dippin.entity.connection.repository.ConnectionRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DatabaseTest
class ConnectionServiceTest {

    @Autowired
    private ConnectionService connectionService;

    @Autowired
    private TestCurrentTimeProvider currentTimeProvider;
    @Autowired
    private ConnectionStatusUpdateService connectionStatusUpdateService;
    @Autowired
    private ConnectionRepository connectionRepository;

    @Test
    void updateConnectionStatus() {
        //given
        int memberId = 1;

        LocalDateTime now = LocalDateTime.now();
        currentTimeProvider.setCurrentTime(now);

        // when
        connectionService.updateConnectionStatus(memberId);

        // then
        ConnectionStatusEntity connection = connectionRepository.findByMemberId(memberId);

        assertThat(memberId).isEqualTo(connection.getMemberId());
        assertThat(connection.getLastActiveAt()).isEqualTo(now);
    }
}