package com.kato.challengecart.service;

import com.kato.challengecart.controller.response.PaymentOptionsDto;
import com.kato.challengecart.controller.response.PaymentsInfoDto;
import com.kato.challengecart.domain.ShoppingCart;
import com.kato.challengecart.domain.User;
import com.kato.challengecart.service.strategy.PaymentStrategyFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

@Service
public class PaymentService {

    private final PaymentStrategyFactory strategyFactory;

    public PaymentService(PaymentStrategyFactory strategyFactory) {
        this.strategyFactory = strategyFactory;
    }

    public PaymentOptionsDto calculatePaymentOptions(User user, ShoppingCart cart) {
        var strategy = strategyFactory.getPaymentStrategy(cart);
        var paymentOptions = strategy.calculatePaymentOptions(user, cart);
        return getPaymentOptions(paymentOptions, user);
    }

    private PaymentOptionsDto getPaymentOptions(Map<Integer, BigDecimal> paymentOptions, User user) {
        List<PaymentsInfoDto> paymentList = paymentOptions.entrySet().stream()
                .map(entry -> new PaymentsInfoDto(entry.getKey(), applySeniorDiscount(entry.getValue(), user)))
                .toList();
        return new PaymentOptionsDto(paymentList);
    }

    private BigDecimal applySeniorDiscount(BigDecimal totalValue, User user) {
        if (user.getAge() >= 60) {
            return totalValue.multiply(BigDecimal.valueOf(0.99)).setScale(2, RoundingMode.HALF_UP);
        }
        return totalValue;
    }

}
