package com.sngular.test.inditex.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "prices")
@Setter
@Getter
public class PriceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "brand_id", nullable = false)
    private int brandId;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @Column(name = "price_list", nullable = false)
    private int priceList;

    @Column(name = "product_id", nullable = false)
    private int productId;

    @Column(name = "priority", nullable = false)
    private int priority;

    @Column(name = "price", nullable = false, precision = 4, scale = 2)
    private BigDecimal price;

    @Column(name = "currency", nullable = false)
    private String currency;
}