package com.parting.dippin.entity.connection;

import com.parting.dippin.domain.connection.ConnectionStatus;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "connection")
@Getter
@NoArgsConstructor
public class ConnectionStatusEntity {

    @Id
    private int memberId;
    private LocalDateTime lastActiveAt;
    private boolean isPlaying;

    @Builder
    private ConnectionStatusEntity(int memberId, LocalDateTime lastActiveAt, boolean isPlaying) {
        this.memberId = memberId;
        this.lastActiveAt = lastActiveAt;
        this.isPlaying = isPlaying;
    }

    public static ConnectionStatusEntity from(ConnectionStatus connectionStatus) {
        return ConnectionStatusEntity.builder()
                .memberId(connectionStatus.getMemberId())
                .lastActiveAt(connectionStatus.getLastActiveAt())
                .isPlaying(connectionStatus.isPlaying())
                .build();
    }
}
