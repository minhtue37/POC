package com.poc.ecommerce.reward;

import com.poc.ecommerce.reward.interfaces.rest.RewardController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RewardApplicationTest {

    @Autowired
    private RewardController rewardController;

    @Test
    void contextLoads() {
        Assertions.assertNotNull(rewardController);
    }

}
