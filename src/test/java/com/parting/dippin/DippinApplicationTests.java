package com.parting.dippin;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.parting.dippin.common.TestContainerConfig;
import java.time.ZoneId;
import java.util.TimeZone;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
@ExtendWith(TestContainerConfig.class)
class DippinApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testTimeZoneSetToSeoul() {
        // TimeZoneConfig 가 빈초기화 되는 과정에서 TimeZone 을 Asia/Seoul 로 설정.

        // 현재 시스템의 기본 시간대 확인
        TimeZone currentTimeZone = TimeZone.getDefault();
        ZoneId currentZoneId = currentTimeZone.toZoneId();

        // 기대하는 시간대 (Asia/Seoul)
        ZoneId expectedZoneId = ZoneId.of("Asia/Seoul");

        // 현재 시간대가 Asia/Seoul인지 확인
        assertEquals(expectedZoneId, currentZoneId);
    }

}
