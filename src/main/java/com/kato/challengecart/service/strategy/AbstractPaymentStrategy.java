package com.kato.challengecart.service.strategy;

import com.kato.challengecart.domain.ShoppingCart;
import com.kato.challengecart.domain.User;
import com.kato.challengecart.service.PaymentStrategy;

import java.math.BigDecimal;
import java.util.Map;

public abstract class AbstractPaymentStrategy implements PaymentStrategy {
    @Override
    public Map<Integer, BigDecimal> calculatePaymentOptions(User user, ShoppingCart cart) {
        int maxInstallments = getMaxInstallments(cart);
        return cart.calculateOptions(maxInstallments, user);
    }

    protected abstract int getMaxInstallments(ShoppingCart cart);
}
