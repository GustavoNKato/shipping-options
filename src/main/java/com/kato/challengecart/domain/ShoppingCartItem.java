package com.kato.challengecart.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartItem {
    private Product product;
    private int quantity;

    public BigDecimal calculatePricePerQuantity() {
        return this.getProduct().getPrice().multiply(BigDecimal.valueOf(this.getQuantity()));
    }

}
