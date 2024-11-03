package com.sngular.test.inditex.service;

import com.sngular.test.inditex.model.PriceEntity;
import com.sngular.test.inditex.dto.PriceDto;
import com.sngular.test.inditex.mapper.PriceMapper;
import com.sngular.test.inditex.repository.PriceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.sngular.test.inditex.util.DateUtils.DATE_TIME_FORMATTER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PriceServiceTest {

    private PriceRepository priceRepository;
    private PriceMapper priceMapper;
    private PriceServiceImpl priceService;

    @BeforeEach
    void setUp() {
        priceRepository = mock(PriceRepository.class);
        priceMapper = mock(PriceMapper.class);
        priceService = new PriceServiceImpl(priceRepository, priceMapper);
    }

    @Test
    void givenProductAndBrandAndDateAndMatchingAPrice_whenGetPrice_thenReturnOptionalPrice() {
        // GIVEN productId, brandId and date
        int brandId = 2;
        int productId = 4;
        LocalDateTime date = LocalDateTime.now();
        // WHEN find in repository by  productId, brandId and date
        LocalDateTime dateMinus1day = date.minusDays(1);
        LocalDateTime datePlus1day = date.plusDays(1);
        BigDecimal price15and95 = BigDecimal.valueOf(15.95);
        String euro = "EUR";
        PriceEntity priceEntity = new PriceEntity();
        priceEntity.setId(1);
        priceEntity.setBrandId(brandId);
        priceEntity.setStartDate(dateMinus1day);
        priceEntity.setEndDate(datePlus1day);
        priceEntity.setPriceList(3);
        priceEntity.setProductId(productId);
        priceEntity.setCurrency(euro);
        priceEntity.setPrice(price15and95);
        priceEntity.setPriority(1);
        when(priceRepository.findFirstByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                productId, brandId, date, date))
        // THEN return Optional of price
                .thenReturn(Optional.of(priceEntity));
        // WHEN map entity to DTO
        PriceDto priceDto = PriceDto.builder()
                .productId(productId)
                .brandId(brandId)
                .priceList(3)
                .startDate(DATE_TIME_FORMATTER.format(dateMinus1day))
                .endDate(DATE_TIME_FORMATTER.format(datePlus1day))
                .price(price15and95)
                .currency(euro)
                .build();
        when(priceMapper.asDto(priceEntity))
        // THEN return DTO of price
                .thenReturn(priceDto);
        // WHEN get price by productId, brandId and date
        Optional<PriceDto> result = priceService.getPrice(productId, brandId, date);
        // THEN result is present
        assertTrue(result.isPresent());
        // AND equals to price DTO
        assertEquals(priceDto, result.get());
        // AND verify repository method has been called
        verify(priceRepository).findFirstByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                productId, brandId, date, date);
        // AND verify mapper method has been called
        verify(priceMapper).asDto(priceEntity);
    }

    @Test
    void givenProductAndBrandAndDateAndNoMatchingAPrice_whenGetPrice_thenReturnOptionalNull() {
        // GIVEN
        int productId = 1;
        int brandId = 2;
        LocalDateTime date = LocalDateTime.now();
        // WHEN find in repository by  productId, brandId and date
        when(priceRepository.findFirstByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                productId, brandId, date, date))
        // THEN return Optional null
                .thenReturn(Optional.empty());
        // WHEN get price by productId, brandId and date
        Optional<PriceDto> result = priceService.getPrice(productId, brandId, date);
        // THEN result is not present
        assertFalse(result.isPresent());
        // AND verify repository method has been called
        verify(priceRepository).findFirstByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                productId, brandId, date, date);
    }
}