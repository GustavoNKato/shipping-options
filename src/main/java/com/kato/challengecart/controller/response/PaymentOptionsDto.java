package com.kato.challengecart.controller.response;

import java.util.List;

public record PaymentOptionsDto(List<PaymentsInfoDto> paymentOptions) {
//    public PaymentOptionsDto fromDomain() {
//        return new PaymentOptionsDto(paymentOptions.stream().map(PaymentsInfoDto::fromDomain).toList());
//    }
}
