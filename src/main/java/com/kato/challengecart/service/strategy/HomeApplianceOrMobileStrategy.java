package com.kato.challengecart.service.strategy;

import com.kato.challengecart.domain.ShoppingCart;
import com.kato.challengecart.enums.CategoryEnum;


public class HomeApplianceOrMobileStrategy extends AbstractPaymentStrategy {
    @Override
    protected int getMaxInstallments(ShoppingCart cart) {
        return CategoryEnum.HOME_APPLIANCE.getInstallments();
    }
}
