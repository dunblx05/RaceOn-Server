package com.parting.dippin.domain.connection;

import com.parting.dippin.domain.connection.service.ConnectionStatusUpdateService;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Getter
@NoArgsConstructor
public class ConnectionStatus {

    @Id
    private int memberId;
    private LocalDateTime lastActiveAt;
    private boolean isPlaying;

    @Builder
    private ConnectionStatus(int memberId, LocalDateTime lastActiveAt, boolean isPlaying) {
        this.memberId = memberId;
        this.lastActiveAt = lastActiveAt;
        this.isPlaying = isPlaying;
    }

    public void update(ConnectionStatusUpdateService connectionStatusUpdateService) {
        connectionStatusUpdateService.update(this);
    }
}
