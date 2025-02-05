package com.kato.challengecart.controller.request;


import com.kato.challengecart.domain.ShoppingCartItem;

public record ShoppingCartItemDto(ProductDto product, int quantity) {
    public ShoppingCartItem toDomain() {
        return new ShoppingCartItem(product.toDomain(), quantity);
    }
}
