package com.poc.ecommerce.reward.infrastructure.repositories;

import com.poc.ecommerce.reward.RewardApplication;
import com.poc.ecommerce.reward.domain.model.aggregates.Reward;
import com.poc.ecommerce.reward.domain.model.valueobjects.UserId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Field;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = {RewardRepositoryImpl.class, RewardApplication.class})
public class RewardRepositoryImplTest {
    private static final String USER_A = "user_a";

    @Autowired
    private RewardRepositoryImpl rewardRepositoryImpl;

    @Autowired
    private RewardJPARepository rewardJPARepository;

    @Autowired
    private TestEntityManager entityManager;

    private Reward mockReward(String userId) throws NoSuchFieldException, IllegalAccessException {
        Reward reward = new Reward();
        Field user = reward.getClass().getDeclaredField("userId");
        user.setAccessible(true);
        UserId userObj = new UserId(userId);
        user.set(reward, userObj);
        return reward;
    }

    @Test
    public void testSave() throws NoSuchFieldException, IllegalAccessException {
        Reward newReward = mockReward(USER_A);
        rewardRepositoryImpl.save(newReward);
        assertThat(newReward.getId()).isNotNull();
    }

    @Test
    public void testFindByUserId() throws NoSuchFieldException, IllegalAccessException {
        entityManager.persistAndFlush(mockReward(USER_A));

        Optional<Reward> rewardOptional = rewardRepositoryImpl.findByUserId(USER_A);
        assertThat(rewardOptional.isPresent());
        assertThat(rewardOptional.get().getUserId().getUserId()).isEqualTo(USER_A);
    }
}
