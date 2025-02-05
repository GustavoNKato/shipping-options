package com.kato.challengecart.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ShoppingCart {
    private List<ShoppingCartItem> items;
}
