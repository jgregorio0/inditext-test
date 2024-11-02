package com.sngular.test.inditex.controller;

import com.sngular.test.inditex.dto.PriceDto;
import com.sngular.test.inditex.exception.PriceNotFoundException;
import com.sngular.test.inditex.service.PriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

import static com.sngular.test.inditex.util.DateUtils.DATE_TIME_PATTERN;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PriceController {

    private final PriceService priceService;

    @GetMapping(value = "prices")
    public ResponseEntity<PriceDto> getPrice(
            @RequestParam("productId") int productId,
            @RequestParam("brandId") int brandId,
            @RequestParam("date") @DateTimeFormat(pattern = DATE_TIME_PATTERN) LocalDateTime date)
                throws PriceNotFoundException {
        // ResponseEntity already throws 404 when get price returns empty Optional
        // However, for demonstration of the AdviceController, a custom exception is thrown explicitly
        //return ResponseEntity.of(priceService.getPrice(productId, brandId, date));
        return priceService.getPrice(productId, brandId, date)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new PriceNotFoundException(
                        String.format("No price found for product %d, brand %d and date %s",
                                productId, brandId, date)));
    }

}