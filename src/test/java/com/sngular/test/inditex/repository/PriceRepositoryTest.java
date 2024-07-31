package com.sngular.test.inditex.repository;

import com.sngular.test.inditex.domain.PriceEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class PriceRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PriceRepository priceRepository;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


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
        assertEquals("2020-06-14 00:00:00", found.get().getStartDate().format(formatter));
        assertEquals("2020-12-31 23:59:59", found.get().getEndDate().format(formatter));
        assertEquals(35.50, found.get().getPrice());
        assertEquals(1, found.get().getPriceList());
        assertEquals("EUR", found.get().getCurrency());
        assertEquals(0, found.get().getPriority());
    }
}
