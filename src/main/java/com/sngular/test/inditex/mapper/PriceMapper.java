package com.sngular.test.inditex.mapper;

import com.sngular.test.inditex.domain.PriceEntity;
import com.sngular.test.inditex.dto.PriceDto;
import org.mapstruct.Mapper;

@Mapper
public interface PriceMapper {

    PriceDto asDto(PriceEntity priceEntity);
}
