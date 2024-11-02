package com.sngular.test.inditex.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record PriceDto(int productId,
                       int brandId,
                       int priceList,
                       String startDate,
                       String endDate,
                       BigDecimal price,
                       String currency) {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public BigDecimal getPrice() {
        return price;
    }
}
