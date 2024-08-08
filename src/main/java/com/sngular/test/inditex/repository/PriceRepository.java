package com.sngular.test.inditex.repository;

import com.sngular.test.inditex.domain.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PriceRepository extends JpaRepository<PriceEntity, Integer> {
    Optional<PriceEntity> findFirstByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
            int productId,
            int brandId,
            LocalDateTime startDateAfter,
            LocalDateTime endDateBefore);
}
