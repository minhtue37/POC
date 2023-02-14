package com.poc.ecommerce.reward.infrastructure.repositories;

import com.poc.ecommerce.reward.domain.model.aggregates.Reward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RewardJPARepository extends JpaRepository<Reward, Long> {

    @Query("Select r from Reward r where r.userId.userId = :userId")
    Optional<Reward> findByUserId(@Param("userId") String userId);
}
