package com.kato.challengecart.service;

import com.kato.challengecart.controller.response.PaymentOptionsDto;
import com.kato.challengecart.domain.ShoppingCart;
import com.kato.challengecart.domain.User;
import com.kato.challengecart.service.strategy.PaymentStrategyFactory;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final PaymentStrategyFactory strategyFactory;

    public PaymentService(PaymentStrategyFactory strategyFactory) {
        this.strategyFactory = strategyFactory;
    }

    public PaymentOptionsDto calculatePaymentOptions(User user, ShoppingCart cart) {
        var strategy = strategyFactory.getPaymentStrategy(cart);
        var paymentOptions = strategy.calculatePaymentOptions(user, cart);
        return new PaymentOptionsDto(paymentOptions);
    }
}
