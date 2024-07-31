package com.sngular.test.inditex.controller;

import com.sngular.test.inditex.dto.PriceDto;
import com.sngular.test.inditex.service.PriceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PriceController.class)
public class PriceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    PriceService priceService;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Test
    public void givenFiltersThatMatch1Price_whenGetPrice_thenReturnPrice() throws Exception {
        // GIVEN filters
        final int productId = 1;
        final int brandId = 2;
        final String date = "2024-01-01 00:00:00";
        // WHEN get price
        PriceDto priceFound = PriceDto.builder()
                .productId(productId)
                .brandId(brandId)
                .startDate("2024-01-01 00:00:00")
                .endDate("2024-01-02 00:00:00")
                .price(9.99)
                .priceList(3)
                .currency("EUR").build();
        when(priceService.getPrice(productId, brandId, LocalDateTime.parse(date, formatter)))
                .thenReturn(Optional.of(priceFound));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/prices")
                        .param("productId", String.valueOf(productId))
                        .param("brandId", String.valueOf(brandId))
                        .param("date", date))
        // THEN
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(priceFound.getProductId()))
                .andExpect(jsonPath("$.brandId").value(priceFound.getBrandId()))
                .andExpect(jsonPath("$.startDate").value(priceFound.getStartDate()))
                .andExpect(jsonPath("$.endDate").value(priceFound.getEndDate()))
                .andExpect(jsonPath("$.price").value(priceFound.getPrice()))
                .andExpect(jsonPath("$.priceList").value(priceFound.getPriceList()))
                .andExpect(jsonPath("$.currency").value(priceFound.getCurrency()));
    }

    @Test
    public void givenFiltersThatMatchesNone_whenGetPrice_thenNotFound() throws Exception {
        // GIVEN filters
        final int productId = 2;
        final int brandId = 2;
        final String date = "2024-01-01 00:00:00";
        // WHEN get price
        when(priceService.getPrice(productId, brandId, LocalDateTime.parse(date, formatter)))
                .thenReturn(Optional.empty());
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/prices")
                        .param("productId", String.valueOf(productId))
                        .param("brandId", String.valueOf(brandId))
                        .param("date", date))
        // THEN
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenWrongFilters_whenGetPrice_thenBadRequest() throws Exception {
        // GIVEN filters
        final int productId = 2;
        final int brandId = 2;
        // no date
        // WHEN get price
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/prices")
                        .param("productId", String.valueOf(productId))
                        .param("brandId", String.valueOf(brandId)))
        // THEN
                .andExpect(status().isBadRequest());
    }

}