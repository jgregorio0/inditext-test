package com.sngular.test.inditex.service;

import com.sngular.test.inditex.dto.PriceDto;
import com.sngular.test.inditex.mapper.PriceMapper;
import com.sngular.test.inditex.repository.PriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PriceServiceImpl implements PriceService {

    @Autowired
    private final PriceRepository priceRepository;
    private final PriceMapper priceMapper;

    @Override
    @Cacheable("prices")
    public Optional<PriceDto> getPrice(int productId, int brandId, LocalDateTime date) {
        // Wrap optional value
        return Optional.ofNullable(
                // map as DTO
                priceMapper.asDto(
                        // find first price that matches filters ordered by priority descending
                        // so the first element is the highest priority price
                        priceRepository
                                .findFirstByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                                        productId, brandId, date, date)
                                .orElse(null)));
    }
}
