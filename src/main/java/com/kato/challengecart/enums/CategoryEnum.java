package com.kato.challengecart.enums;

import lombok.Getter;

@Getter
public enum CategoryEnum {
    DECOR(3),
    ELECTRONIC(6),
    HOME_APPLIANCE(12),
    MOBILE_PHONE(12);

    private final int installments;

    CategoryEnum(int installments) {
        this.installments = installments;

    }
}
