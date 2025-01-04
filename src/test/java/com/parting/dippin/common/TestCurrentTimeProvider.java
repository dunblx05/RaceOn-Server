package com.parting.dippin.common;

import com.parting.dippin.core.common.CurrentTimeProvider;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("test")
@Component
public class TestCurrentTimeProvider implements CurrentTimeProvider {

    LocalDateTime currentTime;

    /**
     * MySQL의 DateTime은 초단위 까지만 반올림하여 저장하지만,
     * Java의 시간 정밀도는 더 세밀한 단위까지 저장한다.
     * 테스트에서 이 차이를 매꾸기위해 단위를 조정하였다.
     * @param currentTime
     */
    public void setCurrentTime(LocalDateTime currentTime) {

        this.currentTime = currentTime.truncatedTo(ChronoUnit.SECONDS);
    }

    @Override
    public LocalDateTime now() {
        return currentTime;
    }
}
