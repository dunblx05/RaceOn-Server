package com.parting.dippin.core.common;

import java.time.LocalDateTime;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("!test")
@Component
public class CurrentTimeProviderImpl implements CurrentTimeProvider {

    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}
