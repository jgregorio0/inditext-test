package com.sngular.test.inditex.mapper;

import com.sngular.test.inditex.domain.PriceEntity;
import com.sngular.test.inditex.dto.PriceDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface PriceMapper {

    @Mapping(source = "startDate", target = "startDate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(source = "endDate", target = "endDate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    PriceDto asDto(PriceEntity priceEntity);
}
