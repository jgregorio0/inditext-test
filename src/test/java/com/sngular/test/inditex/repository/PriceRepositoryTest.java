package com.sngular.test.inditex.repository;

import com.sngular.test.inditex.domain.PriceEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.sngular.test.inditex.util.DateUtils.DATE_TIME_FORMATTER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_CLASS;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_CLASS;

@DataJpaTest
@Sql(scripts = {"/insert_prices_data.sql"}, executionPhase = BEFORE_TEST_CLASS)
@Sql(scripts = {"/delete_prices_data.sql"}, executionPhase = AFTER_TEST_CLASS)
@ActiveProfiles("test")
public class PriceRepositoryTest {

    @Autowired
    private PriceRepository priceRepository;

    @Test
    void givenFilterMatchingMultiplePrices_whenFindFirst_thenHighestPriorityIsReturned() {
        // GIVEN testing data inserted by insert_prices_data.sql
        // id, brand_id, start_date,                end_date,           price_list, product_id, priority, price,    currency
        // (1,       1, '2020-06-14 00:00:00', '2020-12-31 23:59:59',   1,          35455,      0,          35.50, 'EUR'),
        // (2,       1, '2020-06-14 15:00:00', '2020-06-14 18:30:00',   2,          35455,      1,          25.45, 'EUR'),
        // AND filter matching multiple results
        int filterBrandId = 1;
        int filterProductId = 35455;
        LocalDateTime filterDate = LocalDateTime.from(
                DATE_TIME_FORMATTER.parse("2020-06-14 16:00:00"));
        // WHEN find first
        Optional<PriceEntity> found = priceRepository
                .findFirstByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                        filterProductId, filterBrandId, filterDate, filterDate);
        // THEN returns highest priority price id = 2
        assertTrue(found.isPresent());
        assertEquals(2, found.get().getId());
    }

    @Test
    void givenNullFilter_whenFindFirst_thenHighestPriorityIsReturned() {
        // GIVEN null filter
        // WHEN find first
        Optional<PriceEntity> found = priceRepository
                .findFirstByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                        0, 0, null, null);
        // THEN returns highest priority price id = 2
        assertTrue(found.isEmpty());
    }
}
