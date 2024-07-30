package com.sngular.test.inditex.service;

import com.sngular.test.inditex.dto.PriceDto;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PriceService {

    Optional<PriceDto> getPrice(int productId, int brandId, LocalDateTime date);

}
