package com.sngular.test.inditex.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class PriceDto {

    private int productId;
    private int brandId;
    private int priceList;
    private String startDate;
    private String endDate;
    private double price;
    private String currency;

}
