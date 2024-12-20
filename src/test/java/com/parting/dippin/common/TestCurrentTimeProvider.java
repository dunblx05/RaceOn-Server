package com.parting.dippin.common;

import com.parting.dippin.core.common.CurrentTimeProvider;
import java.time.LocalDateTime;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("test")
@Component
public class TestCurrentTimeProvider implements CurrentTimeProvider {

    LocalDateTime currentTime;

    public void setCurrentTime(LocalDateTime currentTime) {
        this.currentTime = currentTime;
    }

    @Override
    public LocalDateTime now() {
        return currentTime;
    }
}
