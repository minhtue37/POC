package com.poc.ecommerce.reward.infrastructure.repositories;

import com.poc.ecommerce.reward.domain.model.valueobjects.StickerHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StickerHistoryCachingRepository extends CrudRepository<StickerHistory, String> {
}
