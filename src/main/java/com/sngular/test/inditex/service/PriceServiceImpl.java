package com.sngular.test.inditex.service;

import com.sngular.test.inditex.dto.PriceDto;
import com.sngular.test.inditex.exception.PriceNotFoundException;
import com.sngular.test.inditex.mapper.PriceMapper;
import com.sngular.test.inditex.repository.PriceRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PriceServiceImpl implements PriceService {

    private static final Logger logger = LoggerFactory.getLogger(PriceServiceImpl.class);

    private final PriceRepository priceRepository;
    private final PriceMapper priceMapper;

    @Override
    @Cacheable("prices")
    public Optional<PriceDto> getPrice(int productId, int brandId, LocalDateTime date)
            throws PriceNotFoundException {
        logger.debug("Getting price by product {}, brand {} and date {}",
                productId, brandId, date);// just to add some logs for the test
        // find first price that matches filters ordered by priority descending
        // so the first element is the highest priority price
        return priceRepository
                .findFirstByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                        productId, brandId, date, date)
                // When price is found then map to DTO and return value as Optional
                .map(price -> {
                    logger.info("Found price with id {}", price.getId());
                    return Optional.of(priceMapper.asDto(price));
                })
                // When price is not found then throw PriceNotFoundException
                .orElseThrow(() -> {
                    String message = String.format("No price found for product %d, brand %d and date %s",
                            productId, brandId, date);
                    logger.warn(message);
                    return new PriceNotFoundException(message);
                });
    }
}
