package com.sngular.test.inditex.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class PriceDto {

    private int productId;
    private int brandId;
    private int priceList;
    private String startDate;
    private String endDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal price;
    private String currency;

}
