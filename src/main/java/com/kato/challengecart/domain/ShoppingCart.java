package com.kato.challengecart.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ShoppingCart {
    private static final BigDecimal DISCOUNT_THRESHOLD_1 = BigDecimal.valueOf(2000);
    private static final BigDecimal DISCOUNT_THRESHOLD_2 = BigDecimal.valueOf(5000);
    private static final BigDecimal DISCOUNT_RATE_1 = BigDecimal.valueOf(0.975);
    private static final BigDecimal DISCOUNT_RATE_2 = BigDecimal.valueOf(0.95);
    private static final BigDecimal FEE_RATE_1 = BigDecimal.valueOf(1.01);
    private static final BigDecimal FEE_RATE_2 = BigDecimal.valueOf(1.02);
    private static final BigDecimal FEE_RATE_3 = BigDecimal.valueOf(1.03);
    private static final int SCALE = 2;
    public static final int SIX_PAYMENTS = 6;
    public static final int THREE_PAYMENTS = 3;
    public static final int UNIQUE_PAY = 1;

    private List<ShoppingCartItem> items;

    public Map<Integer, BigDecimal> calculateOptions(int maxInstallments) {
        var paymentOptionsMapper = new HashMap<Integer, BigDecimal>();
        var totalPrice = calculateTotalPrice();
        for (int installment = 1; installment <= maxInstallments; installment++) {
            var installmentValue = calculateInstallmentWithTax(totalPrice, installment);
            paymentOptionsMapper.put(installment, installmentValue);
        }
        return paymentOptionsMapper;
    }

    private BigDecimal calculateTotalPrice() {
        return this.getItems().stream()
                .map(ShoppingCartItem::calculatePricePerQuantity)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateInstallmentWithTax(BigDecimal totalValue, int installments) {
        if (installments > UNIQUE_PAY) {
            var fees = applyFees(totalValue, installments);
            return applyScale(fees);
        }
        var discount = applyDiscount(totalValue);
        return applyScale(discount);
    }

    private BigDecimal applyFees(BigDecimal totalValue, int installments) {
        if (installments <= THREE_PAYMENTS) {
            return totalValue.multiply(FEE_RATE_1.pow(installments)).divide(BigDecimal.valueOf(installments), SCALE, RoundingMode.HALF_UP);
        }
        if (installments <= SIX_PAYMENTS) {
            return totalValue.multiply(FEE_RATE_2.pow(installments)).divide(BigDecimal.valueOf(installments),SCALE, RoundingMode.HALF_UP);
        }
        return totalValue.multiply(FEE_RATE_3.pow(installments)).divide(BigDecimal.valueOf(installments), SCALE, RoundingMode.HALF_UP);
    }


    private BigDecimal applyDiscount(BigDecimal totalValue) {
        if (totalValue.compareTo(DISCOUNT_THRESHOLD_1) <= 0) {
            return totalValue;
        }
        if (totalValue.compareTo(DISCOUNT_THRESHOLD_2) <= 0) {
            return totalValue.multiply(DISCOUNT_RATE_1);
        }
        return totalValue.multiply(DISCOUNT_RATE_2);
    }

    private BigDecimal applyScale(BigDecimal value) {
        return value.setScale(SCALE, RoundingMode.HALF_UP);

    }
}
