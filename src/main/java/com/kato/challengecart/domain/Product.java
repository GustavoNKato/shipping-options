package com.kato.challengecart.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Product {
    private String category;
    private String title;
    private BigDecimal price;
}
