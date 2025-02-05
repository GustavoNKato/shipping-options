package com.kato.challengecart.controller.request;


import com.kato.challengecart.domain.ShoppingCart;
import com.kato.challengecart.domain.ShoppingCartItem;

import java.util.List;

public record ShoppingCartDto(List<ShoppingCartItemDto> items) {
    public ShoppingCart toDomain() {
        List<ShoppingCartItem> domainItems = items.stream()
                .map(ShoppingCartItemDto::toDomain)
                .toList();
        return new ShoppingCart(domainItems);
    }
}
