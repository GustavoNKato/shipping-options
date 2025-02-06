package com.kato.challengecart.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    public static final BigDecimal DISCOUNT_SENIOR = BigDecimal.valueOf(0.99);
    private static final int SCALE = 2;
    public static final int DISCOUNT_THRESHOLD_SENIOR = 60;

    private String firstName;
    private String lastName;
    private Integer age;

    public void applySeniorDiscount(Map<Integer, BigDecimal> paymentOptions) {
        if (this.age >= DISCOUNT_THRESHOLD_SENIOR) {
            paymentOptions.replaceAll((key, value) ->
                    value.multiply(DISCOUNT_SENIOR).setScale(SCALE, RoundingMode.HALF_UP));
        }
    }
}
