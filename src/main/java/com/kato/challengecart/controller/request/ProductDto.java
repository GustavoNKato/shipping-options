package com.kato.challengecart.controller.request;


import com.kato.challengecart.domain.Product;

import java.math.BigDecimal;

public record ProductDto(String category, String title, BigDecimal price) {
    public Product toDomain() {
        return new Product(category, title, price);
    }
}
