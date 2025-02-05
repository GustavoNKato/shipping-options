package com.kato.challengecart.service;

import com.kato.challengecart.controller.response.PaymentOptionsDto;
import com.kato.challengecart.controller.response.PaymentsInfoDto;
import com.kato.challengecart.domain.ShoppingCart;
import com.kato.challengecart.domain.User;
import com.kato.challengecart.enums.CategoryEnum;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class PaymentsOptionsService {
    public PaymentOptionsDto calculatePaymentOptions(User user, ShoppingCart shoppingCart) {
        BigDecimal totalValue = calculateTotalValue(shoppingCart);
        Set<String> categoriesSet = getCategoriesSet(shoppingCart);
        Map<Integer, BigDecimal> paymentsMap = getPayments(categoriesSet, totalValue);
        return getPaymentOptions(paymentsMap, user);
    }

    // test pr
    private Set<String> getCategoriesSet(ShoppingCart shoppingCart) {
        return shoppingCart.getItems().stream()
                .map(item -> item.getProduct().getCategory())
                .collect(Collectors.toSet());
    }

    private BigDecimal calculateTotalValue(ShoppingCart shoppingCart) {
        return shoppingCart.getItems().stream()
                .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


    // Forte candidato para aplicar strategy pattern
    // Atraves da categoria e do valor total - uso o strategy para ter o algoritmo que varifica quantas parcelas,
    // o valor da parcela e aplica juros ou desconto
    private Map<Integer, BigDecimal> getPayments(Set<String> categoriesSet, BigDecimal totalValue) {
        Map<Integer, BigDecimal> paymentOptionsMapper = new HashMap<>();
        int maxInstallments = getMaxInstallments(categoriesSet, totalValue); // aqui esta pegando o numero de parcelas
        for (int i = 1; i <= maxInstallments; i++) {
            var installmentValue = calculateInstallmentWithTax(totalValue, i);
            paymentOptionsMapper.put(i, installmentValue);
        }
        return paymentOptionsMapper;
    }

    private Integer getMaxInstallments(Set<String> categoriesSet, BigDecimal totalValue) {
        if (totalValue.compareTo(BigDecimal.valueOf(5000)) >= 0 ||
                categoriesSet.contains(CategoryEnum.MOBILE_PHONE.toString()) ||
                categoriesSet.contains(CategoryEnum.HOME_APPLIANCE.toString())) {
            return 12;
        }
        if (categoriesSet.contains(CategoryEnum.ELECTRONIC.toString())) {
            return 6;
        }
        return 3;
    }

    private BigDecimal calculateInstallmentWithTax(BigDecimal totalValue, int installments) {
        BigDecimal installmentValue;
        if (installments == 1) {
            installmentValue = applyDiscount(totalValue);
        } else {
            installmentValue = applyFees(totalValue, installments);
        }
        return installmentValue.setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal applyFees(BigDecimal totalValue, int installments) {
        BigDecimal feesRate;
        if (installments <= 3) {
            feesRate = BigDecimal.valueOf(1.01);
        } else if (installments <= 6) {
            feesRate = BigDecimal.valueOf(1.02);
        } else {
            feesRate = BigDecimal.valueOf(1.03);
        }
        return totalValue
                .multiply(feesRate.pow(installments))
                .divide(BigDecimal.valueOf(installments), 2, RoundingMode.HALF_UP);
    }

    private BigDecimal applyDiscount(BigDecimal totalValue) {
        if (totalValue.compareTo(BigDecimal.valueOf(2000)) <= 0) {
            return totalValue;
        } else if (totalValue.compareTo(BigDecimal.valueOf(5000)) <= 0) {
            return totalValue.multiply(BigDecimal.valueOf(0.975));
        } else {
            return totalValue.multiply(BigDecimal.valueOf(0.95));
        }
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
