package com.kato.challengecart.controller.response;

import java.math.BigDecimal;

public record PaymentsInfoDto(int numberOfInstallments, BigDecimal amount) {
}
