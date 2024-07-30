package com.sngular.test.inditex.repository;

import com.sngular.test.inditex.domain.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PriceRepository extends JpaRepository<PriceEntity, Integer>, JpaSpecificationExecutor<PriceEntity> {
}
