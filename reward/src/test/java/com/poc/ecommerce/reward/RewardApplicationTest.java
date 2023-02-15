package com.poc.ecommerce.reward;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = {RewardApplication.class})
class RewardApplicationTest {


    @Test
    void contextLoads() {
        RewardApplication.main(new String[]{});
    }

}
