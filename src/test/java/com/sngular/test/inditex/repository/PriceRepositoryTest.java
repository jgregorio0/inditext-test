package com.sngular.test.inditex.repository;

import com.sngular.test.inditex.domain.PriceEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
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
    void testFind() {
        // Given
        final int priceId = 1;
        // When
        Optional<PriceEntity> found = priceRepository.findById(priceId);
        // Then returns (1, 1, '2020-06-14 00:00:00', '2020-12-31 23:59:59', 1, 35455, 0, 35.50, 'EUR'),
        assertTrue(found.isPresent());
        assertEquals(priceId, found.get().getId());
        assertEquals(35455, found.get().getProductId());
        assertEquals(1, found.get().getBrandId());
        assertEquals("2020-06-14 00:00:00", found.get().getStartDate().format(DATE_TIME_FORMATTER));
        assertEquals("2020-12-31 23:59:59", found.get().getEndDate().format(DATE_TIME_FORMATTER));
        assertEquals(0, BigDecimal.valueOf(35.50).compareTo(found.get().getPrice()));
        assertEquals(1, found.get().getPriceList());
        assertEquals("EUR", found.get().getCurrency());
        assertEquals(0, found.get().getPriority());
    }
}
