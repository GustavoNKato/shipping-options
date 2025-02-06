package com.kato.challengecart.service.strategy;

import com.kato.challengecart.domain.ShoppingCart;
import com.kato.challengecart.enums.CategoryEnum;
import com.kato.challengecart.service.PaymentStrategy;
import org.springframework.stereotype.Component;

@Component
public class PaymentStrategyFactory {
    public PaymentStrategy getPaymentStrategy(ShoppingCart cart) {
        if (cartContainsCategory(cart, CategoryEnum.HOME_APPLIANCE.name()) || cartContainsCategory(cart, CategoryEnum.MOBILE_PHONE.name())) {
            return new HomeApplianceOrMobileStrategy();
        } else if (cartContainsCategory(cart, CategoryEnum.ELECTRONIC.name())) {
            return new ElectronicStrategy();
        } else {
            return new DefaultStrategy();
        }
    }

    private boolean cartContainsCategory(ShoppingCart cart, String category) {
        return cart.getItems().stream()
                .anyMatch(item -> item.getProduct().getCategory().equalsIgnoreCase(category));
    }

}
