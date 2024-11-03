package com.sngular.test.inditex.mapper;

import com.sngular.test.inditex.model.PriceEntity;
import com.sngular.test.inditex.dto.PriceDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static com.sngular.test.inditex.util.DateUtils.DATE_TIME_PATTERN;

@Mapper
public interface PriceMapper {

    @Mapping(source = "startDate", target = "startDate", dateFormat = DATE_TIME_PATTERN)
    @Mapping(source = "endDate", target = "endDate", dateFormat = DATE_TIME_PATTERN)
    PriceDto asDto(PriceEntity priceEntity);
}
