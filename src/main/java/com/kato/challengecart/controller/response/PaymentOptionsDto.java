package com.kato.challengecart.controller.response;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


public record PaymentOptionsDto(List<PaymentsInfoDto> paymentOptions) {
    public PaymentOptionsDto(Map<Integer, BigDecimal> paymentOptions) {
        this(paymentOptions.entrySet().stream()
                .map(entry -> new PaymentsInfoDto(entry.getKey(), entry.getValue()))
                .toList());
    }
}
