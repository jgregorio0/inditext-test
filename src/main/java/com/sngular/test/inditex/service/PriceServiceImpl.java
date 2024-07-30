package com.sngular.test.inditex.service;

import com.sngular.test.inditex.domain.PriceEntity;
import com.sngular.test.inditex.dto.PriceDto;
import com.sngular.test.inditex.mapper.PriceMapper;
import com.sngular.test.inditex.repository.PriceRepository;
import com.sngular.test.inditex.repository.specification.PriceSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        return priceRepository
                // find all prices
                .findAll(
                        // filtering by product, brand and date
                        PriceSpecification.getProductPrices(productId, brandId, date),
                        // paging by 1, sorting by priority descending
                        PageRequest.of(0, 1, Sort.by("priority").descending()))
                // so the first element is the highest priority price
                .get()
                .findFirst()
                // map as DTO
                .map(priceMapper::asDto);
    }
}
