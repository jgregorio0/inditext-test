package com.sngular.test.inditex.service;

import com.sngular.test.inditex.dto.PriceDto;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PriceService {

    Optional<PriceDto> getProductPrice(int productId, int brandId, LocalDateTime date);

}
