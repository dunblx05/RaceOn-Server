package com.parting.dippin.core.scheduler;

import com.parting.dippin.entity.connection.ConnectionStatusEntity;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Slf4j
@Component
@RequiredArgsConstructor
public class ConnectionStatusScheduler {

    private static final int TEN_MINUTE = 1000 * 10;
    private static final int SCAN_SIZE = 100;
    private static final String PREFIX = "connection:";
    private static final String CONNECTION_PATTERN = "connection:*";

    private static final ScanOptions scanOptions = ScanOptions.scanOptions()
            .count(SCAN_SIZE)
            .match(CONNECTION_PATTERN)
            .build();
    public static final int VALID_LENGTH = 2;
    public static final int VALUE_INDEX = 1;
    public static final String KEY_VALUE_DELIMITER = ":";

    private final RedisTemplate<Integer, ConnectionStatusEntity> connectionRedisTemplate;
    private final ConnectionSchedulerService connectionSchedulerService;

    @Scheduled(fixedDelay = TEN_MINUTE)
    public void writeBackVotes() {
        Set<Integer> redisKeys = scanForKeys();
        if (ObjectUtils.isEmpty(redisKeys)) {
            return;
        }

        for (Integer memberId : redisKeys) {
            connectionSchedulerService.syncConnectionStatus(memberId);
        }
    }

    private Set<Integer> scanForKeys() {
        Set<Integer> keys = new HashSet<>();
        connectionRedisTemplate.execute(getVoidRedisCallback(keys));

        return keys;
    }

    private static RedisCallback<Void> getVoidRedisCallback(Set<Integer> keys) {
        return connection -> {
            try (Cursor<byte[]> cursor = connection.keyCommands().scan(scanOptions)) {
                while (cursor.hasNext()) {
                    String nextStr = new String(cursor.next());

                    if (nextStr.startsWith(PREFIX)) {
                        String[] keyValues = nextStr.split(KEY_VALUE_DELIMITER);

                        if (hasValue(keyValues)) {
                            int value = Integer.parseInt(keyValues[VALUE_INDEX]);
                            keys.add(value);
                        }
                    }
                }
            } catch (Exception e) {
                log.error("scan Redis keys error");
            }

            return null;
        };
    }

    private static boolean hasValue(String[] keyValues) {
        return keyValues.length == VALID_LENGTH;
    }
}
