package com.sngular.test.inditex.repository.specification;

import com.sngular.test.inditex.domain.PriceEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class PriceSpecification {

    public static Specification<PriceEntity> getProductPrices(
            int productId, int brandId, LocalDateTime date) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.and(
                equalProductId(productId, root, criteriaBuilder),
                equalBrandId(brandId, root, criteriaBuilder),
                lessThanOrEqualToStartDate(date, root, criteriaBuilder),
                greaterThanOrEqualToEndDate(date, root, criteriaBuilder));
    }

    private static Predicate greaterThanOrEqualToEndDate(LocalDateTime date, Root<PriceEntity> root, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.greaterThanOrEqualTo(root.get("endDate"), date);
    }

    private static Predicate lessThanOrEqualToStartDate(LocalDateTime date, Root<PriceEntity> root, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.lessThanOrEqualTo(root.get("startDate"), date);
    }

    private static Predicate equalBrandId(int brandId, Root<PriceEntity> root, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get("brandId"), brandId);
    }

    private static Predicate equalProductId(int productId, Root<PriceEntity> root, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get("productId"), productId);
    }
}
