package com.kato.challengecart.service.strategy;

import com.kato.challengecart.domain.ShoppingCart;

public class DefaultStrategy extends AbstractPaymentStrategy{

    @Override
    protected int getMaxInstallments(ShoppingCart cart) {
        return 3;
    }
}
