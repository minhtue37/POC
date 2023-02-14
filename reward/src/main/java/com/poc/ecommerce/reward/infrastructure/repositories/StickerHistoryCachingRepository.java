package com.poc.ecommerce.reward.infrastructure.repositories;

import com.poc.ecommerce.shareddomain.model.StickerHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StickerHistoryCachingRepository extends CrudRepository<StickerHistory, String> {
}
