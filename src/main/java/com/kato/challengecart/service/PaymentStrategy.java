package com.kato.challengecart.service;

import com.kato.challengecart.domain.ShoppingCart;
import com.kato.challengecart.domain.User;

import java.math.BigDecimal;
import java.util.Map;

public interface PaymentStrategy {
    Map<Integer, BigDecimal> calculatePaymentOptions(User user, ShoppingCart cart);
}
