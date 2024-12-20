package com.parting.dippin;

import com.parting.dippin.common.TestContainerConfig;
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

}
